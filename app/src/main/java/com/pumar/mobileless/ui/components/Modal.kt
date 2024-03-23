package com.pumar.mobileless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Modal(
    onDismissRequest: () -> Unit,
    width: Dp = 300.dp,
    height: Dp,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {

    val currentContent by rememberUpdatedState(content)

    Dialog(onDismissRequest, properties) {
        Box(
            modifier = Modifier
                .width(width)
                .height(height)
                .background(color = Color.Black)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp) // Adjust the radius as needed
                )
            ) {
                Column (modifier = Modifier.padding(16.dp)) {
                    currentContent()
                }
            }
    }
}