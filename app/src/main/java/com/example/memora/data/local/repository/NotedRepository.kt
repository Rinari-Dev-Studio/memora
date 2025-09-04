package com.example.memora.data.local.repository

import com.example.memora.data.local.dao.NotedDao
import com.example.memora.data.local.model.Noted
import kotlinx.coroutines.flow.Flow

class NotedRepository(private val notedDao: NotedDao) {
    val allNotes: Flow<List<Noted>> = notedDao.getAllNotes()

    suspend fun insert(note: Noted) {
        notedDao.insert(note)
    }

    suspend fun delete(note: Noted) {
        notedDao.delete(note)
    }

    suspend fun update(note: Noted) {
        notedDao.update(note)
    }
}