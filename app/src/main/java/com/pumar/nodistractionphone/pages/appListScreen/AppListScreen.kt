package com.pumar.nodistractionphone.pages.appListScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pumar.nodistractionphone.emptyTripleList
import com.pumar.nodistractionphone.entities.IApp
import com.pumar.nodistractionphone.pages.appListScreen.components.ListAllApps
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme

@Composable
fun AppListScreen(allAppsList: List<IApp>, updateAllAppList: () -> Unit) {
    Surface (
        color = MaterialTheme.colorScheme.primary // Change the color according to your theme
    ){
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