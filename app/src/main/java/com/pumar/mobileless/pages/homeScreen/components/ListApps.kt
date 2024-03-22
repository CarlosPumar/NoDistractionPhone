package com.pumar.mobileless.pages.homeScreen.components

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.os.Process
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.ui.components.App
import com.pumar.mobileless.ui.components.AppDialog
import com.pumar.mobileless.viewModels.AppListViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListApps() {
    val context: Context = LocalContext.current

    var appListViewModel: AppListViewModel = viewModel()
    val appList by appListViewModel.allAppList.collectAsState()
    val favAppsList = appList.filter { it.isFavorite }

    val appToShow = remember { mutableStateOf<String?>(null) }

    var handleShowDialog = { packageName: String ->
        {
            appToShow.value = packageName
        }
    }
    var hideModal = { appToShow.value = null }

    if (appToShow.value != null) {
        AppDialog(appToShow.value!!, hideModal, {})
    }

    if (favAppsList != null) LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 50.dp, 0.dp, 0.dp),
    ) {
        items(favAppsList.size) { index ->
            val it = favAppsList[index]
            App(it, handleShowDialog(it.packageName))
        }
    }
}

@Preview
@Composable
fun ListAppsPreview() {
    ListApps()
}