package com.pumar.mobileless

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

class MainActivity : ComponentActivity() {

    private var allAppsList = mutableStateOf(emptyList<IApp>())
    private var favAppsList = mutableStateOf(emptyList<IApp>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateAppsList()

        setContent {
            NoDistractionPhoneTheme {
                // A surface container using the 'background' color from the theme
                SwipeableScreens(allAppsList.value, favAppsList.value) { updateFavList() }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAppsList()
    }

    private fun updateAppsList() {
        val installedApps = getAllInstalledApps(this).sortedBy { it.name }
        allAppsList.value = installedApps

        val sharedPreferences = this.getSharedPreferences("favApps", Context.MODE_PRIVATE)
        val favAppListPlain = sharedPreferences.getString("list", "")
        val appList =
            if (favAppListPlain != null) parseStringToArray(favAppListPlain).toList() else emptyList()

        favAppsList.value = installedApps.filter { appList.contains(it.packageName) }
    }

    private fun updateFavList() {
        val sharedPreferences = this.getSharedPreferences("favApps", Context.MODE_PRIVATE)
        val favAppListPlain = sharedPreferences.getString("list", "")
        val appList =
            if (favAppListPlain != null) parseStringToArray(favAppListPlain).toList() else emptyList()

        favAppsList.value = allAppsList.value.filter { appList.contains(it.packageName) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableScreens(
    allAppsList: List<IApp>,
    favAppsList: List<IApp>,
    updateAppList: () -> Unit,
) {

    var phoneUsageTime = 0L
    allAppsList.forEach { app -> phoneUsageTime += app.usageTime }

    val context = LocalContext.current

    val pagerState = rememberPagerState()

    val sharedPrefs = remember { context.getSharedPreferences("favApps", Context.MODE_PRIVATE) }

    DisposableEffect(sharedPrefs) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "list") {
                updateAppList()
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
        updateAppList()
        onDispose {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    HorizontalPager(pageCount = 2, state = pagerState) { page ->
        when (page) {
            0 -> HomeScreen(phoneUsageTime, favAppsList)
            1 -> AppListScreen(allAppsList, updateAppList)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {

    NoDistractionPhoneTheme {
        SwipeableScreens(emptyList(), emptyList(), {})
    }
}