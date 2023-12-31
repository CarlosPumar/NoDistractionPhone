package com.pumar.nodistractionphone.pages.homeScreen.components

import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
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
import androidx.compose.ui.unit.dp
import com.pumar.nodistractionphone.utils.formatMillisToHoursMinutes
import com.pumar.nodistractionphone.utils.getAllInstalledApps
import com.pumar.nodistractionphone.utils.launchApp
import com.pumar.nodistractionphone.utils.todayMillis
import java.util.Calendar
import java.util.TimeZone

@Composable
fun UsageTime() {

    val context = LocalContext.current

    val usageTime = remember { mutableStateOf<Long>(0) }

    LaunchedEffect(Unit) {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = todayMillis()

        val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime)
        val installedApps = getAllInstalledApps(context)

        var newUsageTime: Long = 0
        for (app in installedApps) {
            for (usage in usageStatsList) {
                if (app.packageName == usage.packageName) {
                    newUsageTime += usage.totalTimeInForeground
                    break
                }
            }
        }

        usageTime.value = newUsageTime
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tiempo de uso " + formatMillisToHoursMinutes(usageTime.value))
        }
    }
}