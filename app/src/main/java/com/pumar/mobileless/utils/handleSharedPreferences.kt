package com.pumar.mobileless.utils

import android.content.Context

fun addFavApp(context: Context, packageName: String) {
    val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)
    val editor = sharedPrefs.edit()

    // Accessing stored data
    var storedValue = sharedPrefs.getString("list", "") ?: ""

    val appList = parseStringToArray(storedValue)
    val appMutableList = appList.toMutableList()

    appMutableList.add(packageName)
    editor.putString("list", stringifyArray(appMutableList.toTypedArray()))
    editor.apply()
}

fun deleteFavApp(context: Context, packageName: String) {
    val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)
    val editor = sharedPrefs.edit()

    // Accessing stored data
    var storedValue = sharedPrefs.getString("list", "") ?: ""

    val appList = parseStringToArray(storedValue)
    val appMutableList = appList.toMutableList()

    if (appList.contains(packageName)) {
        appMutableList.remove(packageName)
        editor.putString("list", stringifyArray(appMutableList.toTypedArray()))
        editor.apply()
    }
}

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