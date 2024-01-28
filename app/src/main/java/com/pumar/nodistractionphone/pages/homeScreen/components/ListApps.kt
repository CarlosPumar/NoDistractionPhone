package com.pumar.nodistractionphone.pages.homeScreen.components

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.os.Process
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pumar.nodistractionphone.emptyTripleList
import com.pumar.nodistractionphone.entities.IApp
import com.pumar.nodistractionphone.ui.components.App
import com.pumar.nodistractionphone.ui.components.AppDialog
import com.pumar.nodistractionphone.utils.parseStringToArray

@Composable
fun ListApps(favAppsList: List<IApp>) {
    val context: Context = LocalContext.current

    val appToShow = remember { mutableStateOf("") }
    val packageNameToShow = remember { mutableStateOf("") }

    LaunchedEffect(context) {
        handleUsageStatsPermission(context)
    }

    var handleShowDialog = {appName: String, packageName: String -> { appToShow.value = appName; packageNameToShow.value = packageName } }
    var hideModal = { appToShow.value = ""; packageNameToShow.value = "" }

    if (appToShow.value != "" && packageNameToShow.value != "") {
        AppDialog(appToShow.value, packageNameToShow.value, hideModal, {})
    }

    if (favAppsList != null) LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 50.dp, 0.dp, 0.dp),
    ) {
        items(favAppsList.size) { index ->
            val it = favAppsList[index]
            App(it.packageName, it.name, it.usageTime, handleShowDialog(it.name, it.packageName))
        }
    }
}

fun checkUsageStatsPermission(context: Context): Boolean {
    val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOpsManager.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), context.packageName
        )
    } else {
        appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), context.packageName
        )
    }

    return mode == AppOpsManager.MODE_ALLOWED
}

fun handleUsageStatsPermission(context: Context) {
    if (!checkUsageStatsPermission(context)) {
        // If permission is not granted, navigate the user to the settings screen
        Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
            context.startActivity(this)
        }
    }
}

@Preview
@Composable
fun ListAppsPreview() {
    ListApps(emptyList())
}