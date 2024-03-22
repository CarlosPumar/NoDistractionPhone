package com.pumar.mobileless

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.pages.appListScreen.AppListScreen
import com.pumar.mobileless.pages.homeScreen.HomeScreen
import com.pumar.mobileless.ui.theme.NoDistractionPhoneTheme
import com.pumar.mobileless.utils.getAllInstalledApps
import com.pumar.mobileless.utils.parseStringToArray
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.ui.components.DisclaimerDialog
import com.pumar.mobileless.utils.checkUsageStatsPermission
import com.pumar.mobileless.utils.handleUsageStatsPermission
import com.pumar.mobileless.viewModels.AppListViewModel
import com.pumar.mobileless.viewModels.FilterViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val appListViewModel: AppListViewModel by viewModels()
        super.onCreate(savedInstanceState)
        appListViewModel.updateAppsList(this)

        setContent {
            NoDistractionPhoneTheme {
                // A surface container using the 'background' color from the theme
                SwipeableScreens()
            }
        }
    }

    override fun onResume() {
        val appListViewModel: AppListViewModel by viewModels()
        val filterViewModel: FilterViewModel by viewModels()
        appListViewModel.allAppList.value.forEach {
            Log.d(TAG, it.name)
            Log.d(TAG, it.isFavorite.toString())
        }
        super.onResume()
        appListViewModel.updateAppsList(this)
        filterViewModel.onChanche("")
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableScreens() {

    val context = LocalContext.current
    var appListViewModel: AppListViewModel = viewModel()

    var phoneUsageTime = 0L
    appListViewModel.allAppList.value.forEach { app -> phoneUsageTime += app.usageTime }

    val pagerState = rememberPagerState()

    var hasPermissions by remember { mutableStateOf(true) }

    LaunchedEffect(context) {
        hasPermissions = checkUsageStatsPermission(context)
    }

    HorizontalPager(pageCount = 2, state = pagerState) { page ->
        when (page) {
            0 -> HomeScreen(phoneUsageTime)
            1 -> AppListScreen()
        }
    }
    if (!hasPermissions) {
        DisclaimerDialog { hasPermissions = false }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {

    NoDistractionPhoneTheme {
        SwipeableScreens()
    }
}