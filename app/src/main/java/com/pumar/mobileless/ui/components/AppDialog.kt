package com.pumar.mobileless.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pumar.mobileless.utils.addFavApp
import com.pumar.mobileless.utils.deleteFavApp
import com.pumar.mobileless.utils.isAppBlocked
import com.pumar.mobileless.utils.isFavApp
import com.pumar.mobileless.utils.lengthFavAppList

@Composable
fun AppDialog(
    appName: String,
    packageName: String,
    handleClose: () -> Unit,
    uninstall: (() -> Unit)?
) {

    val context: Context = LocalContext.current

    var stateDefaultValue = isFavApp(context, packageName)
    var state = remember { mutableStateOf(stateDefaultValue) }

    var stateDefaultIsAppBlockedValue = isAppBlocked(context, packageName)
    var blockedAppState = remember { mutableStateOf(stateDefaultIsAppBlockedValue) }

    var height = 200.dp
    var heightState = remember { mutableStateOf(height) }

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
            state.value = false
            return
        }

        if (lengthFavAppList(context) == 5) return

        addFavApp(context, packageName)
        state.value = true
    }

    fun blockApp(time: Int) {
        val time = System.currentTimeMillis() + time

        val sharedPreferences = context.getSharedPreferences("blockedApps", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(packageName, time.toString())
        editor.apply()

        blockedAppState.value = true
        heightState.value = 200.dp
    }

    var boxHeight = if (blockedAppState.value) {
        200.dp
    } else {
        275.dp
    }

    Dialog(onDismissRequest = { handleClose() }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(boxHeight)
                .background(color = Color.Black)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp) // Adjust the radius as needed
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = appName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp) // Adjust the bottom padding as needed
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Favorita ", fontSize = 18.sp, modifier = Modifier.clickable {
                        clickAppToFav(context, packageName)
                    })

                    IconButton(onClick = {
                        clickAppToFav(context, packageName)
                    }) {
                        if (state.value) {
                            Icon(Icons.Filled.Favorite, "Favorite")
                        } else {
                            Icon(Icons.Filled.FavoriteBorder, "No favorite")
                        }
                    }
                }
                Text(text = "Desinstalar", fontSize = 18.sp, modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable {
                        uninstallApp()
                    })

                if (blockedAppState.value) {
                    Text(text = "Está bloqueada", fontSize = 18.sp)
                } else {

                    Text(text = "Bloquear 30m", fontSize = 18.sp, modifier = Modifier
                        .padding(bottom = 12.dp)
                        .clickable {
                            blockApp(1000 * 60 * 30)
                        })

                    Text(text = "Bloquear 1h", fontSize = 18.sp, modifier = Modifier
                        .padding(bottom = 12.dp)
                        .clickable {
                            blockApp(1000 * 60 * 60)
                        })

                    Text(text = "Bloquear 2h", fontSize = 18.sp, modifier = Modifier
                        .clickable {
                            blockApp(1000 * 60 * 60 * 2)
                        })
                }
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