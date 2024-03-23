package com.pumar.mobileless.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterViewModel: ViewModel() {
    private val _filterValue = MutableStateFlow("")
    val filterValue: StateFlow<String> = _filterValue.asStateFlow()

    fun onChange(newFilteredValue: String) {
        _filterValue.update { newFilteredValue }
    }
}