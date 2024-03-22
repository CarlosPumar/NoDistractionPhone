package com.pumar.mobileless.pages.appListScreen

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.pages.appListScreen.components.ListAllApps
import com.pumar.mobileless.pages.appListScreen.components.Search
import com.pumar.mobileless.ui.theme.NoDistractionPhoneTheme
import com.pumar.mobileless.viewModels.FilterViewModel

@Composable
fun AppListScreen() {

    Surface(
        color = MaterialTheme.colorScheme.primary // Change the color according to your theme
    ) {
        Search()
        ListAllApps()
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    NoDistractionPhoneTheme {
        AppListScreen()
    }
}