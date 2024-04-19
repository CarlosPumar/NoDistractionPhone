package com.pumar.mobileless.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.pumar.mobileless.utils.parseStringToArrayInt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
data class Temp(val startDate: Int, val endDate: Int)

class FocusedModeViewModel: ViewModel() {
    private val _focusedModeValue = MutableStateFlow(false)
    val focusedModeValue: StateFlow<Boolean> = _focusedModeValue.asStateFlow()

    fun refreshFocusedMode(context: Context) {
        val sharedPreferences = context.getSharedPreferences("focusedTime", Context.MODE_PRIVATE)
        val sharedPreferencesConfig = context.getSharedPreferences("focusCustomConf", Context.MODE_PRIVATE)

        // Accessing stored data
        val storedValue = sharedPreferences.getString("time", "0") ?: "0"

        val startHourStored = sharedPreferencesConfig.getInt("startHour", 0)
        val startMinuteStored = sharedPreferencesConfig.getInt("startMinute", 0)
        var startTime = startHourStored * 60 + startMinuteStored

        val endHourStored = sharedPreferencesConfig.getInt("endHour", 0)
        val endMinuteStored = sharedPreferencesConfig.getInt("endMinute", 0)
        var endTime = endHourStored * 60 + endMinuteStored

        val selectedDaysStored = sharedPreferencesConfig.getString("selectedDays", "") ?: ""
        val selectedDays = parseStringToArrayInt(selectedDaysStored)

        // Create list of temps
        val tempList = mutableListOf<Temp>()
        selectedDays.forEach {

            var startTimeCopy = startTime
            var endTimeCopy = endTime

            if (startTime > endTime) {
                endTimeCopy += (24 * 60)
            }

            startTimeCopy += (it * 24 * 60)
            endTimeCopy += (it * 24 * 60)

            tempList.add(Temp(startTimeCopy, endTimeCopy))
        }

        var today = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 7) % 7

        val nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val nowMinute = Calendar.getInstance().get(Calendar.MINUTE)
        val nowTime = nowHour * 60 + nowMinute + today * 24 * 60

        val customMode = tempList.any {
            nowTime > it.startDate && it.endDate > nowTime
        }

        val normalMode = storedValue.toLong() > System.currentTimeMillis()
        _focusedModeValue.update { normalMode || customMode }
    }

    fun setFocusedMode(context: Context, time: Long) {
        val sharedPrefs = context.getSharedPreferences("focusedTime", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val current = System.currentTimeMillis()

        // Add 10 seconds
        val newTime = current + time

        Log.d(TAG, newTime.toString())

        editor.putString("time", newTime.toString())
        editor.apply()
        _focusedModeValue.update { true }
    }
}