package com.pumar.mobileless.pages.appListScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
import com.pumar.mobileless.viewModels.FilterViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListAllApps() {

    val context = LocalContext.current

    var appListViewModel: AppListViewModel = viewModel()
    val appList by appListViewModel.allAppList.collectAsState()

    var viewModel: FilterViewModel = viewModel()
    val filterValue = viewModel.filterValue.collectAsState()

    var filteredAppList = appList.filter {

        // Convert both the filter value and app name to lowercase for case-insensitive comparison
        val filterValueLower = filterValue.value.toLowerCase()
        val appNameLower = it.name.toLowerCase()

        // Check if the app name contains the filter value
        appNameLower.contains(filterValueLower)
    }

    val appToShow = remember { mutableStateOf<String?>(null) }

    var handleShowDialog = { packageName: String ->
        {
            appToShow.value = packageName
        }
    }

    var hideModal = {
        appToShow.value = null
    }

    fun removeApp() {
        if (appToShow.value != null) {
            appListViewModel.removeApp(context, appToShow.value!!)
        }
    }

    val app = appToShow.value
    if (app != null) {
        AppDialog(app, hideModal) { removeApp() }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(25.dp, 75.dp, 0.dp, 0.dp),
    ) {
        items(filteredAppList.size) { index ->

            val it = filteredAppList[index]

            App(
                it,
                handleShowDialog(it.packageName)
            )
        }

    }
}

@Preview
@Composable
fun ListAllAppsPreview() {
    ListAllApps()
}
