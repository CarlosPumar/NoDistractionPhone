package com.pumar.mobileless.pages.appListScreen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.ui.components.App
import com.pumar.mobileless.ui.components.AppDialog

@Composable
fun ListAllApps(appList: List<IApp>, fetchApps: () -> Unit) {

    val appToShow = remember { mutableStateOf("") }
    val packageNameToShow = remember { mutableStateOf("") }

    var handleShowDialog = { appName: String, packageName: String ->
        {
            appToShow.value = appName
            packageNameToShow.value = packageName
        }
    }

    var hideModal = {
        appToShow.value = ""
        packageNameToShow.value = ""
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
        items(appList.size) { index ->

            val it = appList[index]

            App(
                it.packageName,
                it.name,
                it.usageTime,
                handleShowDialog(it.name, it.packageName)
            )
        }

    }
}

@Preview
@Composable
fun ListAllAppsPreview() {
    ListAllApps(emptyList(), {})
}
