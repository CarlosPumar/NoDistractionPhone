package com.pumar.nodistractionphone.utils

import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatMillisToHoursMinutesSeconds(millis: Long): String {

    if (millis <= 0) {
        // Return a default value or an error message if millis is not a valid duration
        return "00:00:00" // Return a default value of 0 hours, 0 minutes, and 0 seconds
    }

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hours))
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes))

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

fun formatMillisToHoursMinutes(millis: Long): String {

    if (millis <= 0) {
        // Return a default value or an error message if millis is not a valid duration
        return "00:00" // Return a default value of 0 hours, 0 minutes, and 0 seconds
    }

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hours))

    return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
}
