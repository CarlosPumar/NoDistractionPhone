package com.pumar.mobileless.utils

import android.content.Context

fun isFavApp(context: Context, packageName: String): Boolean {
    val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)

    // Accessing stored data
    var storedValue = sharedPrefs.getString("list", "") ?: ""

    val appList = parseStringToArray(storedValue)

    return appList.contains(packageName)
}

fun lengthFavAppList(context: Context): Int {
    val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)

    // Accessing stored data
    var storedValue = sharedPrefs.getString("list", "") ?: ""

    val appList = parseStringToArray(storedValue)

    return appList.size
}