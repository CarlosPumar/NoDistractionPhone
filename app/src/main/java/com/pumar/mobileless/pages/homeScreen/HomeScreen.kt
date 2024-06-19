package com.pumar.mobileless.pages.homeScreen

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.R
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.pages.homeScreen.components.Clock
import com.pumar.mobileless.pages.homeScreen.components.Footer
import com.pumar.mobileless.pages.homeScreen.components.Header
import com.pumar.mobileless.pages.homeScreen.components.ListApps
import com.pumar.mobileless.pages.homeScreen.components.UsageTime
import com.pumar.mobileless.ui.theme.NoDistractionPhoneTheme
import com.pumar.mobileless.utils.formatMillisToHoursMinutes
import com.pumar.mobileless.utils.isFocusedModeOn
import com.pumar.mobileless.viewModels.AppListViewModel
import com.pumar.mobileless.viewModels.FilterViewModel
import com.pumar.mobileless.viewModels.FocusedModeViewModel
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun HomeScreen(
    phoneUsageTime: Long,
    modifier: Modifier = Modifier
) {

    var appListViewModel: AppListViewModel = viewModel()
    val appList by appListViewModel.allAppList.collectAsState()

    var focusedModeViewModel: FocusedModeViewModel = viewModel()
    val focusedMode = focusedModeViewModel.focusedModeValue.collectAsState()

    val appListToShow = if (focusedMode.value) {
        appList.filter { it.isNeededInFocus }
    } else {
        appList.filter { it.isFavorite }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary // Change the color according to your theme

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header()
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Clock()
                UsageTime(phoneUsageTime)
                
                if (focusedMode.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.focus_on),
                                fontSize = 18.sp,
                                )
                        }
                    }
                }
                
                ListApps(appListToShow)
            }
            Footer()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    NoDistractionPhoneTheme {
        HomeScreen(0)
    }
}