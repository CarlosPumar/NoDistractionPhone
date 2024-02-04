package com.pumar.nodistractionphone.pages.homeScreen.components

import android.content.Intent
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
        Icon(Icons.Filled.Settings, "Settings")
    }
}