package com.pumar.nodistractionphone.ui.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pumar.nodistractionphone.utils.launchApp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App(packageName: String, name: String, usageTime: String, showDialog: () -> Unit?) {

    val context: Context = LocalContext.current

    Row(
        modifier = Modifier
        .padding(vertical = 7.5.dp)
        .horizontalScroll(rememberScrollState())
        .fillMaxWidth()
    ) {
        Text(
            text = "$name $usageTime",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(0.dp, 7.5.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { launchApp(context, packageName) },
                    onLongClick = { showDialog() }
                )
        )
    }
}