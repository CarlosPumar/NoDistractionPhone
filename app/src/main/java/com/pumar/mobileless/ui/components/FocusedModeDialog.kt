package com.pumar.mobileless.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.R
import com.pumar.mobileless.viewModels.AppListViewModel
import com.pumar.mobileless.viewModels.FocusedModeViewModel

@Composable
fun FocusedModeDialog(handleClose: () -> Unit) {

    var focusedModeViewModel: FocusedModeViewModel = viewModel()
    var appListViewModel: AppListViewModel = viewModel()
    val appList by appListViewModel.allAppList.collectAsState()
    var focusedAppList = appList.filter { it.isNeededInFocus }

    val context: Context = LocalContext.current

    Modal(onDismissRequest = { handleClose() }, height = 450.dp) {
        Row (modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                text = stringResource(R.string.modo_focus_on),
                color = Color.White
            )
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = stringResource(R.string.modo_focus_explanation))
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = stringResource(R.string.modo_focused_selected_apps))
        }

        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            if (focusedAppList.isEmpty()) {
                Text(text = stringResource(R.string.no_focused_apps))
            }

            Column {
                focusedAppList.forEach {
                    Text(text = it.name)
                }
            }
        }

        Text(text = stringResource(R.string.on_per_30m), fontSize = 18.sp, modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 30)
            })

        Text(text = stringResource(R.string.on_per_1h), fontSize = 18.sp, modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 60)
            })

        Text(text = stringResource(R.string.on_per_2h), fontSize = 18.sp, modifier = Modifier
            .clickable {
                focusedModeViewModel.setFocusedMode(context, 1000 * 60 * 60 * 2)
            })
    }
}