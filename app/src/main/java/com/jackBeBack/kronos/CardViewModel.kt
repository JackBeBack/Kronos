package com.jackBeBack.kronos

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room

class CardViewModel(): ViewModel() {

    lateinit var database: AppDatabase

    lateinit var cards: LiveData<List<CardEntity>>

    fun initDB(db: AppDatabase){
       database = db
        cards = database.cardDao().getAll()
    }
}