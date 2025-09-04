package com.example.memora.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.memora.data.local.model.Noted


@Composable
fun NoteItem(
    note: Noted,
    selectedThemeIndex: Int,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (Noted, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var isDeleteMode by remember { mutableStateOf(false) }

    if (isEditing) {
        EditNoteDialog(
            note = note,
            selectedThemeIndex = selectedThemeIndex,
            onConfirm = { newText ->
                onEdit(note, newText)
                isEditing = false
            },
            onDismiss = { isEditing = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = {
                    if (isDeleteMode) {
                        isDeleteMode = false
                    } else {
                        isEditing = true
                    }
                },
                onLongClick = {
                    isDeleteMode = true
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {

            Checkbox(
                checked = note.isDone,
                onCheckedChange = { onToggle() },
                modifier = Modifier.align(Alignment.Top)
            )

            Text(
                text = note.note,
                modifier = Modifier
                    .padding(start = 4.dp, end = 12.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (note.isDone) TextDecoration.LineThrough else null,
                    color = if (note.isDone)
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    else
                        MaterialTheme.colorScheme.onBackground
                )
            )

            if (isDeleteMode) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Löschen",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 8.dp)
                        .size(24.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}
