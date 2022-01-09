package com.sarftec.newsurelygh.data.provider

import com.sarftec.newsurelygh.data.room.NewsDatabase
import com.sarftec.newsurelygh.data.room.mapper.DomainCategoryToRoom
import com.sarftec.newsurelygh.data.room.mapper.RoomCategoryToDomain
import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.tools.ModelMapper
import com.sarftec.newsurelygh.tools.Resource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class CategoryProvider @Inject constructor(
    private val database: NewsDatabase,
    private val domainCategoryToRoom: DomainCategoryToRoom,
    private val roomCategoryToDomain: RoomCategoryToDomain
) {

    private var categories = listOf<Category>()

    suspend fun storeCategories(categories: List<Category>) {
        this.categories = categories
        database.roomCategoryDao().insertCategories(
            categories.mapList(domainCategoryToRoom)
        )
    }

    suspend fun loadCategories() : Resource<Unit> {
        database.roomCategoryDao().fetchCategories().let {
            if(it.isEmpty()) return Resource.error("Error => no categories found in persistence!")
            this.categories = it.mapList(roomCategoryToDomain)
            return Resource.success(Unit)
        }
    }

    suspend fun findCategory(slug: String): Resource<Category> {
        if(categories.isEmpty()) loadCategories()
        val result = categories.firstOrNull {
            it.name.lowercase(Locale.ENGLISH) == slug.lowercase(Locale.ENGLISH)
        }
        return if(result == null) Resource.error("Error => category not found!")
        else Resource.success(result)
    }

    private fun <T, U> List<T>.mapList(mapper: ModelMapper<T, U>): List<U> {
        return map { mapper.map(it) }
    }
}