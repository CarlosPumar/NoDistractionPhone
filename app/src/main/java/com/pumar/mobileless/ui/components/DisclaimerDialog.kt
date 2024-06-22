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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pumar.mobileless.R
import com.pumar.mobileless.utils.handleUsageStatsPermission

@Composable
fun DisclaimerDialog(
    handleClose: () -> Unit,
) {
    val context = LocalContext.current
    val givePermissionsText = stringResource(R.string.give_permissions)

    Modal(onDismissRequest = { handleClose() }, height = 325.dp) {
        Row (modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                text = stringResource(R.string.give_permissions),
                color = Color.White
            )
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = stringResource(R.string.permission_explanation_1), color = Color.White)
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = stringResource(R.string.permission_explanation_2), color = Color.White)
        }
        Row (modifier = Modifier.padding(bottom = 18.dp)) {
            Text(text = stringResource(R.string.permission_explanation_3), color = Color.White)
        }
        Row {
            Text(text = stringResource(R.string.give_permissions), fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier
                .padding(12.dp)
                .clickable {
                    handleUsageStatsPermission(context)
                    handleClose()
                }
                .semantics { contentDescription = givePermissionsText }
            )
        }
    }
}