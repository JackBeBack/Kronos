package com.jackBeBack.kronos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {
    @Query("SELECT * FROM cardentity")
    fun getAll(): LiveData<List<CardEntity>>

    @Query("SELECT * FROM cardentity WHERE uid IN (:cardIds)")
    fun loadAllByIds(cardIds: IntArray): List<CardEntity>

    @Query("SELECT * FROM cardentity WHERE description LIKE :description")
    fun findByDesc(description: String): CardEntity

    @Insert
    fun insertAll(vararg card: CardEntity)

    @Delete
    fun delete(card: CardEntity)

}