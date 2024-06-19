package com.pumar.mobileless.pages.homeScreen.components

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pumar.mobileless.R
import androidx.compose.foundation.layout.size

@Composable
fun Footer(modifier: Modifier = Modifier) {

    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    val openPhoneApp = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    Row(
        modifier = modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {             // Launch the camera in photo mode
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            takePhoto.launch(intent)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.camera_fill),
                contentDescription = "Camera",
                modifier = Modifier.size(24.dp) // Set the size you want here
            )
        }
        IconButton(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End),
            onClick = {
                // Launch the phone app to make a call
                val intent = Intent(Intent.ACTION_DIAL)
                openPhoneApp.launch(intent)
            }
        ) {
            Icon(
                Icons.Rounded.Call,
                contentDescription = "Phone",
                modifier = Modifier.size(24.dp) // Set the size you want here
            )
        }
    }
}