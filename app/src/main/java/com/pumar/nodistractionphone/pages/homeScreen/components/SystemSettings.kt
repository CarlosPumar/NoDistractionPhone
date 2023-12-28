package com.pumar.nodistractionphone.pages.homeScreen.components

import android.content.Intent
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SystemSettings(modifier: Modifier = Modifier) {

    val openSettingsApp = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    Text(text = "Ajustes", modifier = modifier.clickable {             // Launch the camera in photo mode
        // Launch the settings app
        val intent = Intent(Settings.ACTION_SETTINGS)
        openSettingsApp.launch(intent)
    })
}