package com.pumar.mobileless.ui.components

import TimeSelect
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.utils.parseStringToArray
import com.pumar.mobileless.utils.parseStringToArrayInt
import com.pumar.mobileless.utils.parseStringToCalendar
import com.pumar.mobileless.utils.stringifyArray
import com.pumar.mobileless.utils.stringifyArrayInt
import com.pumar.mobileless.utils.timestampToCalendar
import com.pumar.mobileless.viewModels.FocusedModeViewModel
import java.util.*
@Composable
fun IntervalSelectorDialog(handleClose: () -> Unit) {
    var context = LocalContext.current
    var startTime by remember { mutableStateOf(Calendar.getInstance()) }
    var endTime by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDays by remember { mutableStateOf(mutableListOf<Int>()) }
    var focusedModeViewModel: FocusedModeViewModel = viewModel()

    LaunchedEffect(Unit) {
        val sharedPrefs = context.getSharedPreferences("focusCustomConf", Context.MODE_PRIVATE)

        // Accessing stored data
        var startHourStored = sharedPrefs.getInt("startHour", 0)
        var startMinuteStored = sharedPrefs.getInt("startMinute", 0)

        var endHourStored = sharedPrefs.getInt("endHour", 0)
        var endMinuteStored = sharedPrefs.getInt("endMinute", 0)

        var selectedDaysStored = sharedPrefs.getString("selectedDays", "") ?: ""

        val selectedDaysArray = parseStringToArrayInt(selectedDaysStored)
        selectedDays = selectedDaysArray.toMutableList()

        val startCalendar = Calendar.getInstance()
        startCalendar.set(Calendar.HOUR_OF_DAY, startHourStored)
        startCalendar.set(Calendar.MINUTE, startMinuteStored)

        val endCalendar = Calendar.getInstance()
        endCalendar.set(Calendar.HOUR_OF_DAY, endHourStored)
        endCalendar.set(Calendar.MINUTE, endMinuteStored)

        startTime = startCalendar
        endTime = endCalendar
    }

    fun applyConf() {
        val sharedPrefs = context.getSharedPreferences("focusCustomConf", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val startHour = startTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = startTime.get(Calendar.MINUTE)

        val endHour = endTime.get(Calendar.HOUR_OF_DAY)
        val endMinute = endTime.get(Calendar.MINUTE)

        editor.putInt("startHour", startHour)
        editor.putInt("startMinute", startMinute)

        editor.putInt("endHour", endHour)
        editor.putInt("endMinute", endMinute)

        editor.putString("selectedDays", stringifyArrayInt(selectedDays.toTypedArray()))
        editor.apply()
        focusedModeViewModel.refreshFocusedMode(context)
    }

    fun onChangeStartTime(time: Calendar) {
        startTime = time
    }

    fun onChangeEndTime(time: Calendar) {
        endTime = time
    }

    Modal(onDismissRequest = { handleClose() }, height = 1.dp) {

        TimeSelect(startTime, "Start") { onChangeStartTime(it) }
        TimeSelect(endTime, "End") { onChangeEndTime(it) }
        DaySelector(selectedDays) { selectedDays = it }
        Column( modifier = Modifier.padding(16.dp) ) {
            Text("Apply", modifier = Modifier
                .clickable { applyConf(); handleClose() })
        }
    }
}