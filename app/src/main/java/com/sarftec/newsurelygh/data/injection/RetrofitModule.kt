package com.sarftec.newsurelygh.data.injection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sarftec.newsurelygh.data.service.NewsService
import com.sarftec.newsurelygh.data.service.NewsService.Companion.NEWS_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule  {

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(
                    MediaType.get("application/json")
                )
            )
            .build()
    }

    @Provides
    fun newsService(retrofit: Retrofit): NewsService {
        return NewsService.create(retrofit)
    }
}