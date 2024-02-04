package com.pumar.nodistractionphone.utils

import android.app.usage.UsageEvents
import android.app.usage.UsageEvents.Event
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.pumar.nodistractionphone.entities.IApp
import java.util.Calendar

fun launchApp(context: Context, packageName: String) {
    val pm: PackageManager = context.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(packageName)

    launchIntent?.let {
        // Check if the intent exists for the given package name
        context.startActivity(it)
    } ?: run {
        // If the launchIntent is null, the app is not installed
        Toast.makeText(context, "App is not installed", Toast.LENGTH_SHORT).show()
    }
}

fun getNameFromPackageName(context: Context, packageName: String): String {
    val packageManager: PackageManager = context.packageManager
    var appInfo = packageManager.getApplicationInfo(packageName, 0)
    return packageManager.getApplicationLabel(appInfo).toString()
}

fun isAppLaunchable(context: Context, packageName: String): Boolean {
    val pm: PackageManager = context.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(packageName)
    return launchIntent != null && pm.queryIntentActivities(launchIntent, 0).isNotEmpty()
}

fun getAllInstalledApps(context: Context): List<IApp> {
    val packageManager = context.packageManager

    val installedApps = packageManager.getInstalledApplications(0)

    val listAllApps = mutableListOf<IApp>()

    installedApps.forEach {

        if (isAppLaunchable(context, it.packageName)) {

            val newApp = IApp(
                getNameFromPackageName(context, it.packageName),
                it.packageName,
                appUsageTime(context, it.packageName)
            )

            listAllApps.add(newApp)
        }
    }

    return listAllApps
}

fun phoneUsageTime(context: Context): Long {

    val packageManager = context.packageManager
    val installedApps = packageManager.getInstalledApplications(0)
    var usageTime = 0L

    installedApps.forEach {
        if (isAppLaunchable(context, it.packageName)) {
            usageTime += appUsageTime(context, it.packageName)
        }
    }

    return usageTime
}

fun appUsageTime(context: Context, packageName: String): Int {

    // return 0

    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val currentTime = System.currentTimeMillis()
    val usageEvents = usageStatsManager.queryEvents(todayMillis(), currentTime)

    var eventsList = mutableListOf<Map<String, Any>>()

    val usageEvent = Event()
    while (usageEvents.hasNextEvent()) {
        usageEvents.getNextEvent(usageEvent)

        // Filter by packageName
        if (usageEvent.packageName != packageName) continue

        // Filter by event being toForeground or toBackground
        if (usageEvent.eventType != 1 && usageEvent.eventType != 2) continue

        // Create a map to represent the event properties
        val eventMap = mapOf(
            "packageName" to usageEvent.packageName,
            "eventType" to usageEvent.eventType,
            "timeStamp" to usageEvent.timeStamp
        )

        // Add the map to the list
        eventsList.add(eventMap)
    }

    // Sort the list based on timeStamp
    eventsList.sortBy { it["timeStamp"] as Long }

    var usageTime = 0
    var init = todayMillis()

    eventsList.forEach { event ->
        var time = 0

        if (event["eventType"] == 1) {
            init = event["timeStamp"] as Long
        } else if (event["eventType"] == 2) {
            time = (event["timeStamp"] as Long - init).toInt()
        }

        usageTime += time
    }

    return usageTime
}
