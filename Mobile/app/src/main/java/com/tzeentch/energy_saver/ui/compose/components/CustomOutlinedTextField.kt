package com.tzeentch.energy_saver.ui.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    defText: String,
    isError: Boolean = false,
    defTitle: String,
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (defTitle.isNotEmpty()) {
            Text(
                text = defTitle,
                color = Color(0xFFD4D4D5),
                modifier = Modifier.padding(top = 5.dp, start = 18.dp)
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { this.contentDescription = "InputTextField" },
            value = defText,
            placeholder = { Text(text = hint) },
            onValueChange = {
                onValueChange(it)
            },
            isError = isError,
            shape = RoundedCornerShape(22.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(0x4DFDFDFD) )
        )
    }
}