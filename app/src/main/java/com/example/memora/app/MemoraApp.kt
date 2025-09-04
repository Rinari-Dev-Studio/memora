package com.example.memora.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memora.presentation.screens.NotedListScreen
import com.example.memora.presentation.screens.ThemeSelectionScreen
import com.example.memora.presentation.viewmodel.NotedViewModel
import com.example.memora.presentation.viewmodel.ThemeViewModel
import com.example.memora.ui.theme.MemoraTheme


@Composable
fun MemoraApp() {
    val navController: NavHostController = rememberNavController()
    val themeViewModel: ThemeViewModel = viewModel()

    MemoraTheme(themeViewModel = themeViewModel) {
        NavHost(navController = navController, startDestination = "noteList") {
            composable("noteList") {
                val notedViewModel: NotedViewModel = viewModel()
                NotedListScreen(navController, notedViewModel, themeViewModel)
            }

            composable("themeSelection") { ThemeSelectionScreen(navController, themeViewModel) }
        }
    }
}
