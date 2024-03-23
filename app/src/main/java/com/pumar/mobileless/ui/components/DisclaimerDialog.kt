package com.pumar.mobileless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pumar.mobileless.utils.handleUsageStatsPermission

@Composable
fun DisclaimerDialog(
    handleClose: () -> Unit,
) {
    val context = LocalContext.current

    Modal(onDismissRequest = { handleClose() }, height = 325.dp) {
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                text = "Danos permisos",
                color = Color.White
            )
        }
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(text = "Clica en \"Dar permisos\", para poder calcular el tiempo que pasas en cada aplicación ", color = Color.White)
        }
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(text = "¡Tus datos no salen de tú teléfono!", color = Color.White)
        }
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(text = "Únicamente usamos tus datos para mostrártelos, no tratamos tus datos de ninguna otra manera", color = Color.White)
        }
        Row {
            Text(text = "Dar permisos", fontSize = 18.sp, color = Color.White, modifier = Modifier
                .padding(12.dp)
                .clickable {
                    handleUsageStatsPermission(context)
                    handleClose()
                }
            )
        }
    }
}