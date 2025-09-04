package com.example.memora.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memora.R
import com.example.memora.presentation.components.TopAppBarActions
import com.example.memora.presentation.viewmodel.NotedViewModel
import com.example.memora.presentation.viewmodel.ThemeViewModel
import com.example.memora.ui.theme.backgroundLight
import com.example.memora.ui.theme.searchTextField
import com.example.memora.ui.theme.themeOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotedListScreen(
    navController: NavController,
    noteViewModel: NotedViewModel,
    themeViewModel: ThemeViewModel
) {
    val noteList by noteViewModel.noteList.collectAsState()
    val isSorted by noteViewModel.isSorted.collectAsState()
    val searchQuery by noteViewModel.searchQuery.collectAsState()
    val isSearchActive by noteViewModel.isSearchActive.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var newNote by remember { mutableStateOf("") }

    val selectedThemeIndex by themeViewModel.selectedThemeIndex.collectAsState()


    val isVeryLightTheme =
        selectedThemeIndex == themeOptions.size - 1 || selectedThemeIndex == themeOptions.size - 2


    val textColor = if (isVeryLightTheme) Color.Black else MaterialTheme.colorScheme.onBackground
    val borderColor = if (isVeryLightTheme) Color.Black else MaterialTheme.colorScheme.outline
    val cursorColor = if (isVeryLightTheme) Color.Black else MaterialTheme.colorScheme.primary

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),

                title = {
                    Text(
                        "Aufgaben & Gedanken",
                        modifier = Modifier
                            .padding(top = 55.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },

                actions = {
                    Row(
                        modifier = Modifier
                            .padding(top = 50.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopAppBarActions(navController, noteViewModel)
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundLight)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isSorted) "Sortierung: Alphabetisch" else "Sortierung: Standard",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isSearchActive && noteList.isNotEmpty()) {
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { noteViewModel.onSearchQueryChanged(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(searchTextField, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 14.sp,
                                color = Color.Black
                            ),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (searchQuery.isEmpty()) {
                                    Text("Suchen…", fontSize = 14.sp, color = Color.Gray)
                                }
                                innerTextField()
                            }
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                NotedScreen(
                    noteList = noteList,
                    themeViewModel = themeViewModel,
                    onToggle = {
                        noteViewModel.toggleNoteStatus(it)
                        noteViewModel.closeSearch()
                    },
                    onDelete = {
                        noteViewModel.removeNote(it)

                    },
                    onEdit = { note, newTask ->
                        noteViewModel.editNote(note, newTask)
                        noteViewModel.closeSearch()
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundLight)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newNote,
                        onValueChange = { newNote = it },
                        label = {
                            Text(
                                "Memo erstellen",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor
                            )
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = backgroundLight,
                            unfocusedContainerColor = backgroundLight,
                            disabledContainerColor = backgroundLight,
                            focusedBorderColor = borderColor,
                            unfocusedBorderColor = borderColor,
                            disabledBorderColor = borderColor,
                            cursorColor = cursorColor
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (newNote.isNotBlank()) {
                                noteViewModel.addNote(newNote)
                                newNote = ""
                                keyboardController?.hide()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_note),
                            contentDescription = "Plus Icon",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
