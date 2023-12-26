package com.pumar.nodistractionphone.ui.components

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pumar.nodistractionphone.utils.parseStringToArray
import com.pumar.nodistractionphone.utils.stringifyArray

@Composable
fun AppDialog(appName: String, packageName: String, handleClose: () -> Unit, uninstall: (() -> Unit)?) {

    val context: Context = LocalContext.current
    var state = remember { mutableStateOf("-") }

    LaunchedEffect(Unit) {
        val sharedPrefs = context.getSharedPreferences("favApps", Context.MODE_PRIVATE)

        // Accessing stored data
        var storedValue = sharedPrefs.getString("list", "") ?: ""

        val appList = parseStringToArray(storedValue)

        if (appList.contains(appName)) state.value = "*"
        else state.value = "-"
    }

    // Function to handle the result after uninstallation
    val uninstallResultCallback: (Boolean) -> Unit = { isUninstalled ->

        if (isUninstalled) {
            // The app was uninstalled successfully
            // Perform actions after uninstallation here
            if (uninstall != null) uninstall()
            handleClose()
        } else {
            // Uninstallation was cancelled or failed
            // Handle this scenario if needed
        }
    }

    // Create an ActivityResultLauncher to handle uninstallation result
    val uninstallLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForUninstall(uninstallResultCallback)

    // Function to uninstall the app using the package name
    fun uninstallApp() {
        val intent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:$packageName")
        }
        // Start the uninstallation process using the launcher
        uninstallLauncher.launch(intent)
    }

    fun clickAppToFav(context: Context, packageName: String) {
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
            state.value = "-"
            return
        }

        if (appList.size > 5) {
            return
        }

        appMutableList.add(packageName)
        editor.putString("list", stringifyArray(appMutableList.toTypedArray()))
        editor.apply()
        state.value = "*"
    }

    Dialog(onDismissRequest = { handleClose() }) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .background(Color.Blue)
        ) {
            Column() {
                Text(text = appName)
                Row() {
                    Text(text = "Fav ", modifier = Modifier.clickable {
                        clickAppToFav(context, packageName)
                    })
                    Text(text = state.value, modifier = Modifier.clickable {
                        clickAppToFav(context, packageName)
                    })
                }
                Text(text = "Uninstall", modifier = Modifier.clickable {
                    uninstallApp()
                })
            }
        }
    }
}

@Composable
fun rememberLauncherForUninstall(
    callback: (Boolean) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Check if the uninstallation was successful
        val isUninstalled = result.resultCode == android.app.Activity.RESULT_OK
        // Pass the result to the callback function
        callback(isUninstalled)
    }
}