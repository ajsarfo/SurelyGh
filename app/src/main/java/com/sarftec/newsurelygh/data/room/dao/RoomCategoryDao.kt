package com.sarftec.newsurelygh.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.newsurelygh.data.room.ROOM_CATEGORY_TABLE
import com.sarftec.newsurelygh.data.room.entity.RoomCategory

@Dao
interface RoomCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<RoomCategory>)

    @Query("select * from $ROOM_CATEGORY_TABLE")
    suspend fun fetchCategories() : List<RoomCategory>
}