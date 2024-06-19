package com.pumar.mobileless.ui.components

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pumar.mobileless.entities.IApp
import com.pumar.mobileless.utils.formatMillisToHoursMinutes
import com.pumar.mobileless.utils.isAppBlocked
import com.pumar.mobileless.utils.launchApp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App(app: IApp, showDialog: () -> Unit?) {

    val context: Context = LocalContext.current

    val color = if (app.isBlocked) {
        Color.Gray
    } else {
        Color.White
    }

    var usageTimeString = ""
    if (app.usageTime > 1_000 * 60 * 5) {
        usageTimeString = formatMillisToHoursMinutes(app.usageTime.toLong())
    }

    fun launch() {
        if (isAppBlocked(context, app.packageName)) return
        launchApp(context, app.packageName)
    }

    Row(
        modifier = Modifier
            .padding(vertical = 7.5.dp)
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Text(
            text = "${app.name} $usageTimeString",
            color = color,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(0.dp, 7.5.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { launch() },
                    onLongClick = { showDialog() }
                )
        )
    }
}