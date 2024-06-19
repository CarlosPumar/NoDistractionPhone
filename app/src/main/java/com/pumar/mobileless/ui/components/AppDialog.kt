package com.pumar.mobileless.ui.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.R
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.utils.isAppBlocked
import com.pumar.mobileless.utils.isFavApp
import com.pumar.mobileless.utils.isFocusedApp
import com.pumar.mobileless.utils.lengthFavAppList
import com.pumar.mobileless.utils.lengthFocusedAppList
import com.pumar.mobileless.viewModels.AppListViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppDialog(
    packageName: String,
    handleClose: () -> Unit,
    uninstall: (() -> Unit)?
) {

    val context: Context = LocalContext.current

    var appListViewModel: AppListViewModel = viewModel()
    val appList by appListViewModel.allAppList.collectAsState()
    val app = appList.find { it.packageName == packageName }!!

    // Function to handle the result after uninstallation
    val uninstallResultCallback = { isUninstalled: Boolean ->

        if (isUninstalled) {
            // The app was uninstalled successfully
            // Perform actions after uninstallation here
            if (uninstall != null) uninstall()
            appListViewModel.removeAppFromFav(context, app.packageName)
            handleClose()
        }
    }

    // Create an ActivityResultLauncher to handle uninstallation result
    val uninstallLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForUninstall(uninstallResultCallback)

    // Function to uninstall the app using the package name
    fun uninstallApp() {
        val intent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:${app.packageName}")
        }
        // Start the uninstallation process using the launcher
        uninstallLauncher.launch(intent)
    }

    fun clickAppToFav(context: Context, packageName: String) {

        if (isFavApp(context, packageName)) {
            appListViewModel.removeAppFromFav(context, packageName)
            return
        }

        if (lengthFavAppList(context) == 5) return

        appListViewModel.addAppToFav(context, packageName)
    }

    fun clickAppToFocus(context: Context, packageName: String) {

        if (isFocusedApp(context, packageName)) {
            appListViewModel.removeAppFromFocused(context, packageName)
            return
        }

        if (lengthFocusedAppList(context) == 5) return

        appListViewModel.addAppToFocused(context, packageName)
    }

    var boxHeight = if (app.isBlocked) {
        225.dp
    } else {
        300.dp
    }

    Modal(onDismissRequest = { handleClose() }, height = boxHeight) {

        Text(
                text = app.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp) // Adjust the bottom padding as needed
        )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(24.dp)
            ) {
                Text(text = stringResource(R.string.favorite), fontSize = 20.sp, modifier = Modifier.clickable {
                    clickAppToFav(context, app.packageName)
                })

                IconButton(onClick = {
                    clickAppToFav(context, app.packageName)
                }) {
                    if (app.isFavorite) {
                        Icon(Icons.Filled.Favorite, "Favorite", )
                    } else {
                        Icon(Icons.Filled.FavoriteBorder, "No favorite")
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.focus), fontSize = 20.sp, modifier = Modifier.clickable {
                    clickAppToFocus(context, app.packageName)
                })

                IconButton(onClick = {
                    clickAppToFocus(context, app.packageName)
                }) {
                    if (app.isNeededInFocus) {
                        Icon(
                            painter = painterResource(id = R.drawable.circle_filled),
                            contentDescription = "Camera"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.circle_not_filled),
                            contentDescription = "Camera"
                        )
                    }
                }
            }
            Text(text = stringResource(R.string.uninstall), fontSize = 20.sp, modifier = Modifier
                .padding(bottom = 12.dp)
                .clickable {
                    uninstallApp()
                })

            if (app.isBlocked) {
                Text(text = stringResource(R.string.is_blocked), fontSize = 20.sp)
            } else {

                Text(text = stringResource(R.string.block_30m), fontSize = 20.sp, modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable {
                        appListViewModel.blockApp(context, 1000 * 60 * 30, app.packageName)
                    })

                Text(text = stringResource(R.string.block_1h), fontSize = 20.sp, modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable {
                        appListViewModel.blockApp(context, 1000 * 60 * 60, app.packageName)
                    })

                Text(text = stringResource(R.string.block_2h), fontSize = 20.sp, modifier = Modifier
                    .clickable {
                        appListViewModel.blockApp(context, 1000 * 60 * 60 * 2, app.packageName)
                    })
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