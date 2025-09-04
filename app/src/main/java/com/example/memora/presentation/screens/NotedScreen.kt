package com.example.memora.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.memora.data.local.model.Noted
import com.example.memora.presentation.components.NoteItem
import com.example.memora.presentation.viewmodel.ThemeViewModel

@Composable
fun NotedScreen(
    noteList: List<Noted>,
    themeViewModel: ThemeViewModel,
    onToggle: (Noted) -> Unit,
    onDelete: (Noted) -> Unit,
    onEdit: (Noted, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val selectedThemeIndex by themeViewModel.selectedThemeIndex.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 8.dp, horizontal = 0.dp)
    ) {
        items(noteList) { note ->
            Column {
                NoteItem(
                    note = note,
                    selectedThemeIndex = selectedThemeIndex,
                    onToggle = { onToggle(note) },
                    onDelete = { onDelete(note) },
                    onEdit = onEdit,
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 27.dp, vertical = 4.dp)
                )
            }
        }
    }
}