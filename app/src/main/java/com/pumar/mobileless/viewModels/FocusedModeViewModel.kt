package com.pumar.mobileless.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FocusedModeViewModel: ViewModel() {
    private val _focusedModeValue = MutableStateFlow(false)
    val focusedModeValue: StateFlow<Boolean> = _focusedModeValue.asStateFlow()

    fun refreshFocusedMode(context: Context) {
        val sharedPreferences = context.getSharedPreferences("focusedTime", Context.MODE_PRIVATE)

        // Accessing stored data
        var storedValue = sharedPreferences.getString("time", "0") ?: "0"

        val newValue = storedValue.toLong() > System.currentTimeMillis()
        _focusedModeValue.update { newValue }
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