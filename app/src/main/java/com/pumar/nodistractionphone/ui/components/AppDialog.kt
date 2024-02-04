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
import com.pumar.nodistractionphone.utils.addFavApp
import com.pumar.nodistractionphone.utils.deleteFavApp
import com.pumar.nodistractionphone.utils.isFavApp
import com.pumar.nodistractionphone.utils.lengthFavAppList
import com.pumar.nodistractionphone.utils.parseStringToArray
import com.pumar.nodistractionphone.utils.stringifyArray

@Composable
fun AppDialog(appName: String, packageName: String, handleClose: () -> Unit, uninstall: (() -> Unit)?) {

    val context: Context = LocalContext.current

    var stateDefaultValue = if (isFavApp(context, packageName)) "*" else "-"
    var state = remember { mutableStateOf(stateDefaultValue) }


    // Function to handle the result after uninstallation
    val uninstallResultCallback = { isUninstalled: Boolean ->

        if (isUninstalled) {
            // The app was uninstalled successfully
            // Perform actions after uninstallation here
            if (uninstall != null) uninstall()
            deleteFavApp(context, packageName)
            handleClose()
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

        if (isFavApp(context, packageName)) {
            deleteFavApp(context, packageName)
            state.value = "-"
            return
        }

        if (lengthFavAppList(context) == 5) return

        addFavApp(context, packageName)
        state.value = "*"
    }

    Dialog(onDismissRequest = { handleClose() }) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
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
        val isUninstalled = result.resultCode == 0
        callback(isUninstalled)
    }
}