package com.pumar.mobileless.pages.appListScreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.pages.appListScreen.components.ListAllApps
import com.pumar.mobileless.ui.theme.NoDistractionPhoneTheme

@Composable
fun AppListScreen(allAppsList: List<IApp>, updateAllAppList: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary // Change the color according to your theme
    ) {
        ListAllApps(allAppsList, updateAllAppList)
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    NoDistractionPhoneTheme {
        AppListScreen(emptyList(), {})
    }
}