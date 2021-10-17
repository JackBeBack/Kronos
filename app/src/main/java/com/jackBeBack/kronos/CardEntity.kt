package com.jackBeBack.kronos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "description") var description: String
) {

}