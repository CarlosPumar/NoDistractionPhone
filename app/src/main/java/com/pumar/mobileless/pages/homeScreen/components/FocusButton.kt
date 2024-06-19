package com.pumar.mobileless.pages.homeScreen.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.R
import com.pumar.mobileless.pages.appListScreen.components.ListAllApps
import com.pumar.mobileless.ui.components.FocusedModeDialog
import com.pumar.mobileless.viewModels.FilterViewModel
import com.pumar.mobileless.viewModels.FocusedModeViewModel

@Composable
fun FocusButton(modifier: Modifier = Modifier) {

    var showModal by remember { mutableStateOf(false) }

    val onClose = {
        showModal = false
    }

    val onOpen = {
        showModal = true
    }

    IconButton(onClick = onOpen) {
        Icon(
            painter = painterResource(id = R.drawable.circle_filled),
            contentDescription = "Focused",
            modifier = Modifier.size(20.dp) // Set the size you want here
        )
    }

    if (showModal) {
        FocusedModeDialog(onClose)
    }
}