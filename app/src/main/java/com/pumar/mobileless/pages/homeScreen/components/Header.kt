package com.pumar.mobileless.pages.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.viewModels.FocusedModeViewModel

@Composable
fun Header() {

    var focusedModeViewModel: FocusedModeViewModel = viewModel()
    val focusedMode = focusedModeViewModel.focusedModeValue.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        //BatteryState()
        if (!focusedMode.value) {
            FocusButton()
        }
        SystemSettings()
    }
}