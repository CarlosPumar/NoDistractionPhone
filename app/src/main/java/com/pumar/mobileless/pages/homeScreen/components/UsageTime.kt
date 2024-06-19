package com.pumar.mobileless.pages.homeScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pumar.mobileless.R
import com.pumar.mobileless.utils.formatMillisToHoursMinutes

@Composable
fun UsageTime(phoneUsageTime: Long) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.usage_time) + " " + formatMillisToHoursMinutes(phoneUsageTime),
                fontSize = 18.sp,
            )
        }
    }
}