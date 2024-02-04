package com.pumar.nodistractionphone.pages.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Header() {

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End) {
        //BatteryState()
        SystemSettings()
    }
}