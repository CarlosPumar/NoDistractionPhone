package com.pumar.nodistractionphone.pages.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pumar.nodistractionphone.emptyTripleList
import com.pumar.nodistractionphone.entities.IApp
import com.pumar.nodistractionphone.pages.homeScreen.components.BatteryState
import com.pumar.nodistractionphone.pages.homeScreen.components.Clock
import com.pumar.nodistractionphone.pages.homeScreen.components.Footer
import com.pumar.nodistractionphone.pages.homeScreen.components.Header
import com.pumar.nodistractionphone.pages.homeScreen.components.ListApps
import com.pumar.nodistractionphone.pages.homeScreen.components.UsageTime
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme

@Composable
fun HomeScreen(
    phoneUsageTime: Long,
    favAppsList: List<IApp>,
    modifier: Modifier = Modifier
) {

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
                ListApps(favAppsList)
            }
            Footer()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    NoDistractionPhoneTheme {
        HomeScreen(0, emptyList())
    }
}