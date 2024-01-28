package com.pumar.nodistractionphone

import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Process
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
import androidx.core.content.ContextCompat.getSystemService
import com.pumar.nodistractionphone.entities.IApp
import com.pumar.nodistractionphone.pages.appListScreen.AppListScreen
import com.pumar.nodistractionphone.pages.homeScreen.HomeScreen
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme
import com.pumar.nodistractionphone.utils.getAllInstalledApps
import com.pumar.nodistractionphone.utils.parseStringToArray
import com.pumar.nodistractionphone.utils.phoneUsageTime
import com.pumar.nodistractionphone.utils.todayMillis


class MainActivity : ComponentActivity() {

    // Define a mutable state to hold phone usage time
    private var phoneUsage = mutableStateOf(0L)
    private var allAppsList = mutableStateOf(emptyList<IApp>())
    private var favAppsList = mutableStateOf(emptyList<IApp>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updatePhoneUsageTime()
        updateAppsList()

        setContent {
            NoDistractionPhoneTheme {
                // A surface container using the 'background' color from the theme
                SwipeableScreens(phoneUsage.value, allAppsList.value, favAppsList.value) { updateFavList() }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        updatePhoneUsageTime()
        updateAppsList()
    }

    // Function to calculate phone usage time
    private fun updatePhoneUsageTime() {
        phoneUsage.value = phoneUsageTime(this)
    }

    private fun updateAppsList() {
        val installedApps = getAllInstalledApps(this).sortedBy { it.name.replaceFirstChar { firstChar -> firstChar.uppercase() }  }
        allAppsList.value = installedApps

        val sharedPreferences = this.getSharedPreferences("favApps", Context.MODE_PRIVATE)
        val favAppListPlain = sharedPreferences.getString("list", "")
        val appList = if (favAppListPlain != null) parseStringToArray(favAppListPlain).toList() else emptyList()

        favAppsList.value = installedApps.filter { appList.contains(it.packageName) }
    }

    private fun updateFavList() {
        val sharedPreferences = this.getSharedPreferences("favApps", Context.MODE_PRIVATE)
        val favAppListPlain = sharedPreferences.getString("list", "")
        val appList = if (favAppListPlain != null) parseStringToArray(favAppListPlain).toList() else emptyList()

        favAppsList.value = allAppsList.value.filter { appList.contains(it.packageName) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableScreens(
    phoneUsageTime: Long,
    allAppsList: List<IApp>,
    favAppsList: List<IApp>,
    updateAppList: () -> Unit,
) {

    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) // Number of screens

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
        SwipeableScreens(0, emptyList(), emptyList(), {})
    }
}