package com.sarftec.newsurelygh.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarftec.newsurelygh.data.room.dao.RoomCategoryDao
import com.sarftec.newsurelygh.data.room.entity.RoomCategory

@Database(
    entities = [RoomCategory::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun roomCategoryDao() : RoomCategoryDao

    companion object {
        fun getInstance(context: Context) : NewsDatabase {
            return Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                NEWS_DATABASE
            ).build()
        }
    }
}