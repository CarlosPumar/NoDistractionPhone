package com.pumar.mobileless.ui.components
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DaySelector(selectedDays: List<Int>, onDaysSelected: (MutableList<Int>) -> Unit) {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    LazyColumn {
        items(daysOfWeek.size) { index ->
            val day = daysOfWeek[index]
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedDays.contains(index),
                    onCheckedChange = {
                        val newSelectedDays = if (it) {
                            selectedDays.toMutableList().apply { add(index) }
                        } else {
                            selectedDays.toMutableList().apply { remove(index) }
                        }
                        onDaysSelected(newSelectedDays)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = day, fontSize = 18.sp)
            }
        }
    }
}