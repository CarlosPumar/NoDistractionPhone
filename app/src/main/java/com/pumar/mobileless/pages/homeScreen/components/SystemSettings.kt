package com.pumar.mobileless.pages.homeScreen.components

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SystemSettings(modifier: Modifier = Modifier) {

    val openSettingsApp = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    IconButton(onClick = {             // Launch the settings
        // Launch the settings app
        val intent = Intent(Settings.ACTION_SETTINGS)
        openSettingsApp.launch(intent)
    }) {
        Icon(
            Icons.Filled.Settings,
            "Settings",
            modifier = Modifier.size(24.dp) // Set the size you want here
        )
    }
}