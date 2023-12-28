package com.pumar.nodistractionphone.utils

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

fun getNameAndUsageApps(context: Context, packageNameList: List<String>?): Triple<List<String>, List<String>, List<String>>  {

    val packageManager: PackageManager = context.packageManager

    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val endTime = System.currentTimeMillis()
    val startTime = todayMillis()

    val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime)

    val names = mutableListOf<String>()
    val packageNames = mutableListOf<String>()
    val usageTimes = mutableListOf<String>()

    if (packageNameList == null) return Triple(names, packageNames, usageTimes)

    val tripleList = mutableListOf<Triple<String, String, String>>()

    for (packageName in packageNameList) {

        var usage = usageStatsList.find { it.packageName == packageName }

        var appInfo = packageManager.getApplicationInfo(packageName, 0)
        var appName = packageManager.getApplicationLabel(appInfo).toString()
        var usageTime = ""

        if (usage != null) {
            usageTime = formatMillisToHoursMinutes(usage.totalTimeInForeground)
        }

        tripleList.add(Triple(appName.replaceFirstChar { char -> char.titlecase() }, packageName, usageTime))
    }

    val sortedTripleList = tripleList.sortedBy { it.first } // Sorting by appName

    val sortedNames = sortedTripleList.map { it.first }
    val sortedPackageNames = sortedTripleList.map { it.second }
    val sortedUsageTimes = sortedTripleList.map { it.third }

    return Triple(sortedNames, sortedPackageNames, sortedUsageTimes)
}

fun isAppLaunchable(context: Context, packageName: String): Boolean {
    val pm: PackageManager = context.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(packageName)

    return launchIntent != null && pm.queryIntentActivities(launchIntent, 0).isNotEmpty()
}

fun getAllInstalledApps(context: Context): List<ApplicationInfo> {
    val installedApps: List<ApplicationInfo> = context.packageManager.getInstalledApplications(0)
    return installedApps.filter { isAppLaunchable(context, it.packageName) }
}

