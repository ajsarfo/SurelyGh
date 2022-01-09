package com.sarftec.newsurelygh.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.newsurelygh.data.room.ROOM_CATEGORY_TABLE

@Entity(tableName = ROOM_CATEGORY_TABLE)
class RoomCategory(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)