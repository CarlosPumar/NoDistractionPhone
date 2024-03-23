package com.pumar.mobileless.utils

import android.app.usage.UsageEvents.Event
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import com.pumar.mobileless.entities.IApp

fun launchApp(context: Context, packageName: String) {
    val pm: PackageManager = context.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(packageName)

    launchIntent?.let {
        // Check if the intent exists for the given package name
        context.startActivity(it)
    } ?: run {
        // If the launchIntent is null, the app is not installed
        Toast.makeText(context, "Aplicación no instalada", Toast.LENGTH_SHORT).show()
    }
}

fun getNameFromPackageName(context: Context, packageName: String): String {
    val packageManager: PackageManager = context.packageManager
    var appInfo = packageManager.getApplicationInfo(packageName, 0)
    return packageManager.getApplicationLabel(appInfo).toString()
        .replaceFirstChar { firstChar -> firstChar.uppercase() }
}

fun isAppLaunchable(context: Context, packageName: String): Boolean {
    val pm: PackageManager = context.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(packageName)
    return launchIntent != null && pm.queryIntentActivities(launchIntent, 0).isNotEmpty()
}

fun getAllInstalledApps(context: Context): List<IApp> {
    val packageManager = context.packageManager

    // get installed apps
    val installedApps = packageManager.getInstalledApplications(0)
    val listAllApps = mutableListOf<IApp>()
    val eventsList = getUsageEvents(context)

    // get fav apps
    val sharedPreferences = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)
    val favAppListPlain = sharedPreferences.getString("list", "")
    val favAppPackageNameList =
        if (favAppListPlain != null) parseStringToArray(favAppListPlain).toList() else emptyList()

    // get focused apps
    val sharedPreferencesFocused = context.getSharedPreferences("focusedApps", Context.MODE_PRIVATE)
    val focusedAppListPlain = sharedPreferencesFocused.getString("list", "")
    val focusedAppPackageNameList =
        if (focusedAppListPlain != null) parseStringToArray(focusedAppListPlain).toList() else emptyList()

    installedApps.forEach {

        if (isAppLaunchable(context, it.packageName)) {

            val newApp = IApp(
                getNameFromPackageName(context, it.packageName),
                it.packageName,
                appUsageTime(eventsList, it.packageName),
                favAppPackageNameList.contains(it.packageName),
                isAppBlocked(context, it.packageName),
                focusedAppPackageNameList.contains(it.packageName)
            )

            listAllApps.add(newApp)
        }
    }

    return listAllApps
}

fun getUsageEvents(context: Context): MutableList<Map<String, Any>> {
    val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val usageEvents = usageStatsManager.queryEvents(todayMillis(), System.currentTimeMillis())

    var eventsList = mutableListOf<Map<String, Any>>()

    val usageEvent = Event()
    while (usageEvents.hasNextEvent()) {
        usageEvents.getNextEvent(usageEvent)

        // Create a map to represent the event properties
        val eventMap = mapOf(
            "packageName" to usageEvent.packageName,
            "eventType" to usageEvent.eventType,
            "timeStamp" to usageEvent.timeStamp
        )

        // Add the map to the list
        eventsList.add(eventMap)
    }

    return eventsList
}

fun appUsageTime(eventsList: MutableList<Map<String, Any>>, packageName: String): Int {

    var usageTime = 0
    var init = todayMillis()

    eventsList
        .filter { it["packageName"] as String == packageName }
        .forEach { event ->
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

fun isAppBlocked(context: Context, packageName: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("blockedApps", Context.MODE_PRIVATE)

    // Accessing stored data
    var storedValue = sharedPreferences.getString(packageName, "0") ?: "0"

    return storedValue.toLong() > System.currentTimeMillis()
}

fun isFocusedModeOn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("focusedTime", Context.MODE_PRIVATE)

    // Accessing stored data
    var storedValue = sharedPreferences.getString("time", "0") ?: "0"

    return storedValue.toLong() > System.currentTimeMillis()
}
