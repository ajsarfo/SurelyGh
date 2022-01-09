package com.sarftec.newsurelygh.domain.repository

import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.tools.Resource

interface CategoryRepository {
    suspend fun loadCategories() : Resource<Unit>
    suspend fun findCategory(slug: String) : Resource<Category>
}