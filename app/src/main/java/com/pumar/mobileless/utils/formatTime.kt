package com.pumar.mobileless.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun formatMillisToHoursMinutesSeconds(millis: Long): String {

    if (millis <= 0) {
        // Return a default value or an error message if millis is not a valid duration
        return "00:00:00" // Return a default value of 0 hours, 0 minutes, and 0 seconds
    }

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hours))
    val seconds = TimeUnit.MILLISECONDS.toSeconds(
        millis - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes)
    )

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

fun formatMillisToHoursMinutes(millis: Long): String {

    if (millis <= 60000 * 5) {
        // Return a default value or an error message if millis is not a valid duration
        return "" // Return a default value of 0 hours, 0 minutes, and 0 seconds
    }

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hours))

    return String.format(Locale.getDefault(), "%02dh %02dm", hours, minutes)
}

fun todayMillis(): Long {
    val calendar = Calendar.getInstance(TimeZone.getDefault()).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.timeInMillis
}

fun parseStringToCalendar(stringTime: String): Calendar {
    val calendar = Calendar.getInstance()
    if (stringTime.isNotBlank()) {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = timeFormat.parse(stringTime)
        calendar.time = date
    }
    return calendar
}

fun timestampToCalendar(timestamp: Long): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar
}