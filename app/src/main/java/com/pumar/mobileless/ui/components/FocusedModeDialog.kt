package com.pumar.mobileless.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.viewModels.AppListViewModel
import com.pumar.mobileless.viewModels.FocusedModeViewModel

@Composable
fun FocusedModeDialog(handleClose: () -> Unit) {

    var focusedModeViewModel: FocusedModeViewModel = viewModel()
    val context: Context = LocalContext.current

    Modal(onDismissRequest = { handleClose() }, height = 450.dp) {
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                text = "Activar modo Focus",
                color = Color.White
            )
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = "En este modo únicamente podrás usar las apps previamente configuradas como \"Focus\".")
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = "Para esta función, te recomendamos apps como: Navegador Web, Aplicación bancaria y Aplicación de mensajería")
        }

        Text(text = "Activar por 30m", fontSize = 18.sp, modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 30)
            })

        Text(text = "Activar por 1h", fontSize = 18.sp, modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 60)
            })

        Text(text = "Activar por 2h", fontSize = 18.sp, modifier = Modifier
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 60 * 2)
            })
    }
}