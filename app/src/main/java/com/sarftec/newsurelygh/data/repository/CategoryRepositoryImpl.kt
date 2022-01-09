package com.sarftec.newsurelygh.data.repository

import android.util.Log
import com.sarftec.newsurelygh.data.provider.CategoryProvider
import com.sarftec.newsurelygh.data.provider.NewsProvider
import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.domain.repository.CategoryRepository
import com.sarftec.newsurelygh.tools.Resource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryProvider: CategoryProvider,
    private val newsProvider: NewsProvider
) : CategoryRepository {

    override suspend fun loadCategories(): Resource<Unit> {
        val result = categoryProvider.loadCategories().let {
            if (it.isError()) newsProvider.getCategories().also { response ->
                if (response.isSuccess()) categoryProvider.storeCategories(response.data!!)
            }
            else it
        }
        return if (result.isSuccess()) Resource.success(Unit)
        else Resource.error("${result.message}")
    }

    override suspend fun findCategory(slug: String): Resource<Category> {
        return categoryProvider.findCategory(slug)
    }
}