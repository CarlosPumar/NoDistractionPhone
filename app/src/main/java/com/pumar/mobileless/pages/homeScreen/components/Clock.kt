package com.pumar.mobileless.pages.homeScreen.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pumar.mobileless.utils.launchApp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun Clock() {
    val context: Context = LocalContext.current

    // State to hold the current time
    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

    // LaunchedEffect to update the time every second
    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            currentTime.value = getCurrentTime()
            currentDate.value = getCurrentDate()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.clickable { launchApp(context, "com.android.deskclock") },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentTime.value,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = currentDate.value,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Function to get the current time in HH:mm:ss format
private fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    return sdf.format(currentTime)
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    return sdf.format(currentDate)
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Clock()
}
