package com.mekanly.ui.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mekanly.R
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.repository.UserRepository
import com.mekanly.ui.activities.MainActivity

class MekanlyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_REQUEST_CODE = 110
        private const val NOTIFICATION_CHANNEL_ID = "mekanly_notification"
        private const val IMAGE_LOAD_TIMEOUT = 6000L
    }

    private var notificationShown = false



    override fun onNewToken(token: String) {
        super.onNewToken(token)
        AppPreferences.initialize(this)
        AppPreferences.setFirebaseToken(token)
        Log.e("FIREBASE_MANAGER","Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        notificationShown = false
        Log.e("FIREBASE_MANAGER", "message getData: ${remoteMessage.data}")
        Log.e("FIREBASE_MANAGER", "message getNotification: ${remoteMessage.notification}")

        val data: Map<String, String> = remoteMessage.data
        val notification: RemoteMessage.Notification? = remoteMessage.notification

        if (notification != null) {
            val title = notification.title ?: ""
            val body = notification.body ?: ""
            val imageUrl: String = notification.imageUrl.toString()

            if (imageUrl.contentEquals("null")) {
                showNotification(title, body, data, null)
            } else {
                loadImageAndShowNotification(title, body, data, imageUrl)
            }
        }
    }

    private fun loadImageAndShowNotification(
        title: String,
        body: String,
        data: Map<String, String>,
        imageUrl: String
    ) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    showNotification(title, body, data, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    showNotification(title, body, data, null)
                }
            })

        Handler(Looper.getMainLooper()).postDelayed({
            if (!notificationShown) showNotification(title, body, data, null)
        }, IMAGE_LOAD_TIMEOUT)
    }

    private fun showNotification(
        title: String?,
        body: String,
        data: Map<String, String>,
        bitmap: Bitmap?
    ) {
        notificationShown = true
        val intent = createNotificationIntent(data, title)
        val pendingIntent = createPendingIntent(intent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val notificationBuilder =
            pendingIntent?.let { createNotificationBuilder(title, body, it, bitmap) }

        if (notificationBuilder != null) {
            notificationManager.notify(1000, notificationBuilder.build())
        }

        logNotificationEvent(title, bitmap != null)
    }

    private fun createNotificationIntent(data: Map<String, String>, title: String? ): Intent {
        return Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("type", data["type"])
            putExtra("id", data["id"])
            putExtra("openUrl", data["openUrl"])
            putExtra("title", title)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    private fun createPendingIntent(intent: Intent): PendingIntent? {
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(intent)
        return stackBuilder.getPendingIntent(
            NOTIFICATION_REQUEST_CODE,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                enableVibration(true)
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    private fun createNotificationBuilder(
        title: String?,
        body: String,
        pendingIntent: PendingIntent,
        bitmap: Bitmap?
    ): NotificationCompat.Builder {
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(body)

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .apply {
                if (bitmap != null) {
                    setLargeIcon(bitmap)
                    setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                } else {
                    setStyle(bigTextStyle)
                }
            }
    }

    private fun logNotificationEvent(title: String?, hasImage: Boolean) {
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
        val bundle = Bundle()
        title?.let { bundle.putString("title", it) }

        val eventName = if (hasImage) "notification_shown_foreground_image" else "notification_shown_foreground"
        mFirebaseAnalytics.logEvent(eventName, bundle)
    }
}