package com.example.memora.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memora.presentation.viewmodel.ThemeViewModel
import com.example.memora.ui.theme.blendWith
import com.example.memora.ui.theme.themeOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionScreen(
    navController: NavController,
    viewModel: ThemeViewModel
) {
    val selectedThemeIndex by viewModel.selectedThemeIndex.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant.blendWith(Color.Black, 0.3f))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Wähle ein Theme:",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                themeOptions.forEachIndexed { index, theme ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(theme.primary)
                            .clickable {
                                if (index != selectedThemeIndex) {
                                    viewModel.setTheme(index)
                                    navController.popBackStack()
                                }
                            }
                    )
                }
            }
        }
    }
}
