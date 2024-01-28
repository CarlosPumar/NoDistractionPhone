package com.pumar.nodistractionphone.pages.homeScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Header() {

    Row {
        //BatteryState()
        SystemSettings(
            Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End))
    }
}