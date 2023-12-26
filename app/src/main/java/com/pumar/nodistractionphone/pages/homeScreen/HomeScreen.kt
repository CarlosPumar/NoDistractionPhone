package com.pumar.nodistractionphone.pages.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.nodistractionphone.pages.homeScreen.components.BatteryState
import com.pumar.nodistractionphone.pages.homeScreen.components.Clock
import com.pumar.nodistractionphone.pages.homeScreen.components.ListApps
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary // Change the color according to your theme
    ) {
        Column {
            BatteryState()
            Clock()
            ListApps()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    NoDistractionPhoneTheme {
        HomeScreen()
    }
}