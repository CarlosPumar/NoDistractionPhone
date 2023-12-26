package com.pumar.nodistractionphone.pages.appListScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pumar.nodistractionphone.pages.appListScreen.components.ListAllApps
import com.pumar.nodistractionphone.ui.theme.NoDistractionPhoneTheme

@Composable
fun AppListScreen() {
    ListAllApps()
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    NoDistractionPhoneTheme {
        AppListScreen()
    }
}