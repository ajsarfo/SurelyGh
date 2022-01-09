package com.sarftec.newsurelygh.data.service.mapper

import com.sarftec.newsurelygh.data.service.entity.Category
import com.sarftec.newsurelygh.tools.ModelMapper
import javax.inject.Inject

typealias DomainCategory = com.sarftec.newsurelygh.domain.model.Category

class CategoryMapper @Inject constructor(): ModelMapper<Category, DomainCategory> {
    override fun map(from: Category): DomainCategory {
        return DomainCategory(from.id, from.name)
    }
}