package com.example.memora.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memora.presentation.viewmodel.NotedViewModel

@Composable
fun TopAppBarActions(navController: NavController, viewModel: NotedViewModel) {

    Row {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.Sort,
            contentDescription = "Sortieren",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clickable { viewModel.toggleSortOrder() }
                .padding(8.dp)
        )


        Icon(
            imageVector = Icons.Default.Brightness4,
            contentDescription = "Theme ändern",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clickable { navController.navigate("themeSelection") }
                .padding(8.dp)
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Text suchen",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clickable { viewModel.toggleSearch() }
                .padding(8.dp)
        )
    }

}
