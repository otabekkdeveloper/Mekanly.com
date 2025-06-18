package com.mekanly.utils.extensions

import android.app.UiModeManager
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.util.TypedValueCompat


fun Float.dpToPx(): Int {
    return (TypedValueCompat.dpToPx(this, Resources.getSystem().displayMetrics) + 0.5f).toInt()
}

fun Float.pxToDp(): Int {
    return (TypedValueCompat.pxToDp(this, Resources.getSystem().displayMetrics) + 0.5f).toInt()
}

fun getPixels(context: Context, valueInDp: Int): Int {
    val r = context.resources
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), r.displayMetrics
    )
    return px.toInt()
}

fun getPixels(context: Context, valueInDp: Float): Int {
    val r = context.resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, r.displayMetrics)
    return px.toInt()
}

fun getPixelsSp(context: Context, valueInSp: Int): Int {
    val r = context.resources
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, valueInSp.toFloat(), r.displayMetrics
    )
    return px.toInt()
}

fun getPixelsSp(context: Context, valueInSp: Float): Int {
    val r = context.resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.displayMetrics)
    return px.toInt()
}