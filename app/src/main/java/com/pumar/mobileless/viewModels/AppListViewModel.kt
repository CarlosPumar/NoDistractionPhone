package com.pumar.mobileless.viewModels

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.utils.getAllInstalledApps
import com.pumar.mobileless.utils.parseStringToArray
import com.pumar.mobileless.utils.stringifyArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppListViewModel: ViewModel() {
    private val _allAppList = MutableStateFlow(emptyList<IApp>())
    val allAppList: StateFlow<List<IApp>> = _allAppList.asStateFlow()

    fun updateAppsList(context: Context) {
        val installedApps = getAllInstalledApps(context).sortedBy { it.name }
        _allAppList.update { installedApps }
    }

    fun blockApp(context: Context, time: Int, packageName: String) {
        val time = System.currentTimeMillis() + time

        val sharedPreferences = context.getSharedPreferences("blockedApps", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(packageName, time.toString())
        editor.apply()

        _allAppList.update { currentState ->
            val newList = currentState.map { app ->
                val copiedApp = app.copy()
                if (app.packageName == packageName) {
                    copiedApp.isBlocked = true
                }
                copiedApp
            }
            newList
        }
    }

    fun addAppToFav(context: Context, packageName: String) {
        val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // Accessing stored data
        var storedValue = sharedPrefs.getString("list", "") ?: ""

        val appList = parseStringToArray(storedValue)
        val appMutableList = appList.toMutableList()

        appMutableList.add(packageName)
        editor.putString("list", stringifyArray(appMutableList.toTypedArray()))
        editor.apply()

        _allAppList.update {
                current -> current.map {
                val copiedApp = it.copy()
                if (it.packageName == packageName) copiedApp.isFavorite = true
                    copiedApp
            }
        }
    }

    fun removeAppFromFav(context: Context, packageName: String) {
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

        _allAppList.update {
                current -> current.map {
                    val copiedApp = it.copy()
                    if (it.packageName == packageName) copiedApp.isFavorite = false
                    copiedApp
            }
        }
    }

    fun addAppToFocused(context: Context, packageName: String) {
        val sharedPrefs = context.getSharedPreferences("focusedApps", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // Accessing stored data
        var storedValue = sharedPrefs.getString("list", "") ?: ""

        val appList = parseStringToArray(storedValue)
        val appMutableList = appList.toMutableList()

        appMutableList.add(packageName)
        editor.putString("list", stringifyArray(appMutableList.toTypedArray()))
        editor.apply()

        _allAppList.update { current -> current.map {
                val copiedApp = it.copy()
                if (it.packageName == packageName) copiedApp.isNeededInFocus = true
                copiedApp
            }
        }
    }

    fun removeAppFromFocused(context: Context, packageName: String) {
        val sharedPrefs = context.getSharedPreferences("focusedApps", Context.MODE_PRIVATE)
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

        _allAppList.update { current -> current.map {
                val copiedApp = it.copy()
                if (it.packageName == packageName) copiedApp.isNeededInFocus = false
                copiedApp
            }
        }
    }

    fun removeApp(context: Context, packageName: String) {
        _allAppList.update { currentState ->

            currentState.filter { app ->
                app.packageName != packageName
            }
        }

        removeAppFromFav(context, packageName)
    }
}