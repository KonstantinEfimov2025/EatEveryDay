package com.example.eateveryday.database

import androidx.room.*
import com.example.eateveryday.models.DiaryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_table ORDER BY id DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DiaryEntry)

    @Delete
    suspend fun delete(entry: DiaryEntry)
}