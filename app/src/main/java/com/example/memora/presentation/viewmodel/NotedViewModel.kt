package com.example.memora.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.data.PreferencesManager
import com.example.memora.data.local.database.NotedDatabase
import com.example.memora.data.local.model.Noted
import com.example.memora.data.local.repository.NotedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotedRepository
    private val preferencesManager = PreferencesManager(application)

    private val _isSorted = MutableStateFlow(false)
    val isSorted: StateFlow<Boolean> = _isSorted

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    val noteList: StateFlow<List<Noted>>

    init {
        val notedDao = NotedDatabase.getDatabase(application).notedDao()
        repository = NotedRepository(notedDao)

        noteList = combine(
            repository.allNotes, _isSorted, _searchQuery
        ) { notes, sorted, query ->
            val filtered = if (query.isBlank()) {
                notes
            } else {
                notes.filter {
                    it.note.contains(query, ignoreCase = true)
                }
            }
            if (sorted) filtered.sortedBy { it.note.lowercase() } else filtered
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


        viewModelScope.launch {
            preferencesManager.sortOrderFlow.collect { sortOrder ->
                _isSorted.value = sortOrder
            }
        }
    }


    fun toggleSortOrder() {
        viewModelScope.launch {
            val newSortOrder = !_isSorted.value
            _isSorted.value = newSortOrder
            preferencesManager.saveSortOrder(newSortOrder)
        }
    }


    fun addNote(note: String) {
        if (note.isNotBlank()) {
            viewModelScope.launch {
                repository.insert(Noted(note = note))
            }
        }
    }

    fun toggleNoteStatus(note: Noted) {
        viewModelScope.launch {
            val updatedNote = note.copy(isDone = !note.isDone)
            repository.update(updatedNote)
        }
    }

    fun removeNote(note: Noted) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun editNote(note: Noted, newNote: String) {
        viewModelScope.launch {
            val updated = note.copy(note = newNote)
            repository.update(updated)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
    }

    fun closeSearch() {
        _isSearchActive.value = false
    }


}