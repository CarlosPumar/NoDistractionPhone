package com.pumar.nodistractionphone.utils

import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    calendar.add(Calendar.DAY_OF_YEAR, -1)

    val endTime = System.currentTimeMillis()
    val startTime = calendar.timeInMillis

    val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime)

    val names = mutableListOf<String>()
    val packageNames = mutableListOf<String>()
    val usageTimes = mutableListOf<String>()

    if (packageNameList == null) return Triple(names, packageNames, usageTimes)

    val tripleList = mutableListOf<Triple<String, String, String>>()

    for (packageName in packageNameList) {
        for (usage in usageStatsList) {
            if (packageName == usage.packageName) {
                val appInfo = packageManager.getApplicationInfo(usage.packageName, 0)
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val usageTime = formatMillisToHoursMinutes(usage.totalTimeInForeground)

                tripleList.add(Triple(appName.replaceFirstChar{char -> char.titlecase() }, usage.packageName, usageTime))
                break
            }
        }
    }

    val sortedTripleList = tripleList.sortedBy { it.first } // Sorting by appName

    val sortedNames = sortedTripleList.map { it.first }
    val sortedPackageNames = sortedTripleList.map { it.second }
    val sortedUsageTimes = sortedTripleList.map { it.third }

    return Triple(sortedNames, sortedPackageNames, sortedUsageTimes)
}

fun getAllInstalledApps(context: Context): List<ResolveInfo> {
    // searching main activities labeled to be launchers of the apps
    val pm = context.packageManager
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

    val resolvedInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        pm.queryIntentActivities(
            mainIntent,
            PackageManager.ResolveInfoFlags.of(0L)
        )
    } else {
        pm.queryIntentActivities(mainIntent, 0)
    }

    return resolvedInfo
}