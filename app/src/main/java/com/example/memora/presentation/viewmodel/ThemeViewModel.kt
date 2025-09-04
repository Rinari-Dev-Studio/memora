package com.example.memora.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesManager = PreferencesManager(application)


    private val _selectedThemeIndex = MutableStateFlow(0)
    val selectedThemeIndex: StateFlow<Int> = _selectedThemeIndex.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.selectedThemeFlow.collectLatest { index ->
                _selectedThemeIndex.value = index
            }
        }
    }

    fun setTheme(index: Int) {
        viewModelScope.launch {
            preferencesManager.saveTheme(index)
        }
    }

}
