package com.sarftec.newsurelygh.data.injection

import com.sarftec.newsurelygh.data.repository.CategoryRepositoryImpl
import com.sarftec.newsurelygh.data.repository.NewsRepositoryImpl
import com.sarftec.newsurelygh.domain.repository.CategoryRepository
import com.sarftec.newsurelygh.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun postRepository(repository: NewsRepositoryImpl) : NewsRepository

    @Singleton
    @Binds
    fun categoryRepository(repository: CategoryRepositoryImpl) : CategoryRepository
}