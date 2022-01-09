package com.sarftec.newsurelygh.data.injection

import android.content.Context
import com.sarftec.newsurelygh.data.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    fun getDatabase(@ApplicationContext context: Context) : NewsDatabase {
        return NewsDatabase.getInstance(context)
    }
}