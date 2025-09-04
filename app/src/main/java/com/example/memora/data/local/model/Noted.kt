package com.example.memora.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noted_table")
data class Noted(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val note: String,
    val isDone: Boolean = false
)