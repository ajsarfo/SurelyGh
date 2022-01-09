package com.sarftec.newsurelygh.data.room.mapper

import com.sarftec.newsurelygh.data.room.entity.RoomCategory
import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.tools.ModelMapper
import javax.inject.Inject

class DomainCategoryToRoom @Inject constructor(

): ModelMapper<Category, RoomCategory> {
    override fun map(from: Category): RoomCategory {
        return RoomCategory(from.id, from.name)
    }
}