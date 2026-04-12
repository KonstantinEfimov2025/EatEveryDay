package com.example.eateveryday.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateveryday.database.DiaryDatabase
import com.example.eateveryday.models.DiaryEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = DiaryDatabase.getDatabase(application).diaryDao()

    val diaryItems = dao.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            dao.insert(entry)
        }
    }

    fun removeEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            dao.delete(entry)
        }
    }
}