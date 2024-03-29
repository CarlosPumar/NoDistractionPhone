package com.pumar.mobileless.pages.homeScreen.components

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pumar.mobileless.ui.theme.Green
import com.pumar.mobileless.ui.theme.Red
import com.pumar.mobileless.ui.theme.Yellow

@Composable
fun BatteryState() {
    val context = LocalContext.current
    val batteryLevel = remember { mutableStateOf(getBatteryLevel(context)) }

    DisposableEffect(Unit) {
        val batteryLevelReceiver = BatteryBroadcastReceiver { level ->
            batteryLevel.value = level
        }
        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryLevelReceiver, batteryFilter)

        onDispose {
            context.unregisterReceiver(batteryLevelReceiver)
        }
    }

    val color = when {
        batteryLevel.value > 50 -> Green
        batteryLevel.value > 10 -> Yellow
        else -> Red
    }

    Text(
        text = "${batteryLevel.value}%",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = color
    )

}

private fun getBatteryLevel(context: Context): Int {
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

private class BatteryBroadcastReceiver(val onBatteryLevelChanged: (Int) -> Unit) :
    android.content.BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        onBatteryLevelChanged(level)
    }
}

@Preview
@Composable
fun BatteryStatePreview() {
    BatteryState()
}