package com.mekanly.utils.extensions

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatIsoDateLegacy(isoString: String): String {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
//    isoFormat.timeZone = TimeZone.getTimeZone("UTC")

    val date: Date = isoFormat.parse(isoString) ?: return ""
    val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return outputFormat.format(date)
}