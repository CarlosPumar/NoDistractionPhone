import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelect(selectedTime: Calendar, text: String, onChange: (value: Calendar) -> Unit) {

    var context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = remember(selectedTime) { timeFormat.format(selectedTime.time) }

        TextField(
            value = formattedTime,
            onValueChange = { },
            label = { Text(text, color = Color.White) },
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Transparent,
            ),
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                showTimePickerDialog(context) { hours, minutes ->
                    val newSelectedTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hours)
                        set(Calendar.MINUTE, minutes)
                    }

                    newSelectedTime.set(Calendar.HOUR_OF_DAY, hours)
                    newSelectedTime.set(Calendar.MINUTE, minutes)
                    onChange(newSelectedTime)
                }
            }
        )

    }
}

private fun showTimePickerDialog(
    context: android.content.Context,
    onTimeSet: (Int, Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeSet(selectedHour, selectedMinute)
        },
        hour,
        minute,
        true
    ).show()
}