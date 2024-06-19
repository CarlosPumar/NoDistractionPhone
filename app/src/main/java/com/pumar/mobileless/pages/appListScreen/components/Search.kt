package com.pumar.mobileless.pages.appListScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pumar.mobileless.viewModels.FilterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pumar.mobileless.R

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
// fun Search(filterValue: String, onValueChanged: (String) -> Unit) {
fun Search() {

    // Access keyboard controller to handle the keyboard actions
    val keyboardController = LocalSoftwareKeyboardController.current

    var viewModel: FilterViewModel = viewModel()
    val filterValue = viewModel.filterValue.collectAsState()

    Column(
            modifier = Modifier.padding(16.dp) // Add margin around the column
        ) {
            TextField(
                value = filterValue.value,
                onValueChange = {
                    viewModel.onChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp)),
                label = { Text(stringResource(R.string.filter), color = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    textColor = Color.White,
                    containerColor = Color.Black
                )
            )
        }
}