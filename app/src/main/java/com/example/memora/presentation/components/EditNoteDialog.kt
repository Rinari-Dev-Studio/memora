package com.example.memora.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.memora.data.local.model.Noted
import com.example.memora.ui.theme.themeOptions

@Composable
fun EditNoteDialog(
    note: Noted,
    selectedThemeIndex: Int,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(note.note)) }

    val isVeryLightTheme = selectedThemeIndex == themeOptions.size - 1 ||
            selectedThemeIndex == themeOptions.size - 2
    val buttonTextColor =
        if (isVeryLightTheme) Color.Black else themeOptions[selectedThemeIndex].primary

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Memo bearbeiten ") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Neues Memo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp, max = 300.dp)
                    .verticalScroll(rememberScrollState()),
                singleLine = false
            )
        },
        confirmButton = {
            TextButton(onClick = {
                if (text.text.isNotBlank()) {
                    onConfirm(text.text)
                }
            }) {
                Text("Speichern", color = buttonTextColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen", color = buttonTextColor)
            }
        }
    )
}
