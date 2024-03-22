package com.pumar.mobileless.pages.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.pages.homeScreen.components.Clock
import com.pumar.mobileless.pages.homeScreen.components.Footer
import com.pumar.mobileless.pages.homeScreen.components.Header
import com.pumar.mobileless.pages.homeScreen.components.ListApps
import com.pumar.mobileless.pages.homeScreen.components.UsageTime
import com.pumar.mobileless.ui.theme.NoDistractionPhoneTheme

@Composable
fun HomeScreen(
    phoneUsageTime: Long,
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
                ListApps()
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