package com.example.memora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memora.data.local.model.Noted
import kotlinx.coroutines.flow.Flow

@Dao
interface NotedDao {
    @Query("SELECT * FROM noted_table")
    fun getAllNotes(): Flow<List<Noted>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(note: Noted)

    @Delete
    suspend fun delete(note: Noted)

    @Update
    suspend fun update(note: Noted)
}