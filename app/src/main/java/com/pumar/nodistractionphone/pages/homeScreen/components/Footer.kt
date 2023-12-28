package com.pumar.nodistractionphone.pages.homeScreen.components

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pumar.nodistractionphone.utils.launchApp

@Composable
fun Footer(modifier: Modifier = Modifier) {

    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    val openPhoneApp = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result if needed
        // You can check result.resultCode and result.data if necessary
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Cámara", modifier = Modifier.clickable {             // Launch the camera in photo mode
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhoto.launch(intent)
        })
        Text(text = "Teléfono",
            modifier = Modifier
            .weight(1f)
            .wrapContentWidth(Alignment.End)
            .clickable {
                // Launch the phone app to make a call
                val intent = Intent(Intent.ACTION_DIAL)
                openPhoneApp.launch(intent)
            }
        )
    }
}