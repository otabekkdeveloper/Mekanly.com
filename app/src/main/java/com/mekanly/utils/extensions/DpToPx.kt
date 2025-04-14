package com.mekanly.utils.extensions

import android.content.res.Resources
import androidx.core.util.TypedValueCompat

/**
 * Convert dp to pixels
 */
fun Float.dpToPx(): Int {
    return (TypedValueCompat.dpToPx(this, Resources.getSystem().displayMetrics) + 0.5f).toInt()
}

/**
 * Convert pixels to dp
 */
fun Float.pxToDp(): Int {
    return (TypedValueCompat.pxToDp(this, Resources.getSystem().displayMetrics) + 0.5f).toInt()
}
