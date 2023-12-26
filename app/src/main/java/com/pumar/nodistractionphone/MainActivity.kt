package com.pumar.nodistractionphone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.nodistractionphone.pages.appListScreen.AppListScreen
import com.pumar.nodistractionphone.pages.homeScreen.HomeScreen
import com.pumar.nodistractionphone.pages.appListScreen.components.ListAllApps
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoDistractionPhoneTheme {
                // A surface container using the 'background' color from the theme
                SwipeableScreens()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableScreens() {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) // Number of screens

    val mapAllApps = "all aps here TODO"

    HorizontalPager(pageCount = 2, state = pagerState) { page ->
        when (page) {
            0 -> HomeScreen()
            // 1 -> AppListScreen(mapAllApps)
            1 -> AppListScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {

    NoDistractionPhoneTheme {
        SwipeableScreens()
    }
}