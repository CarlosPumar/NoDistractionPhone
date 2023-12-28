package com.pumar.nodistractionphone.pages.appListScreen.components
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pumar.nodistractionphone.ui.components.App
import com.pumar.nodistractionphone.ui.components.AppDialog
import com.pumar.nodistractionphone.utils.getAllInstalledApps
import com.pumar.nodistractionphone.utils.getNameAndUsageApps

@Composable
fun ListAllApps() {

    val context: Context = LocalContext.current

    val appListState = remember {
        mutableStateOf(Triple<List<String>, List<String>, List<String>>(emptyList(), emptyList(), emptyList()))
    }
    val appToShow = remember { mutableStateOf("") }
    val packageNameToShow = remember { mutableStateOf("") }

    var handleShowDialog = {appName: String, packageName: String -> {
        appToShow.value = appName
        packageNameToShow.value = packageName
    } }

    var hideModal = {
        appToShow.value = ""
        packageNameToShow.value = ""
    }

    val (names, packageNames, usageTimes) = appListState.value

    val fetchApps = {
        val installedApps = getAllInstalledApps(context)
        appListState.value = getNameAndUsageApps(context, installedApps.map { it.packageName })
    }

    LaunchedEffect(Unit) {
        fetchApps()
    }

    if (appToShow.value != "" && packageNameToShow.value != "") {
        AppDialog(appToShow.value, packageNameToShow.value, hideModal, fetchApps)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(25.dp, 50.dp, 0.dp, 0.dp),
    ) {
        items(names.size) { index ->
            val name = names[index]
            val packageName = packageNames[index]
            val usageTime = usageTimes[index]

            App(packageName, name, usageTime, handleShowDialog(name, packageName))
        }
    }
}

@Preview
@Composable
fun ListAllAppsPreview() {
    ListAllApps()
}
