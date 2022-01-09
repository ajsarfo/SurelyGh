package com.sarftec.newsurelygh.data.room.mapper

import com.sarftec.newsurelygh.data.room.entity.RoomCategory
import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.tools.ModelMapper
import javax.inject.Inject

class RoomCategoryToDomain @Inject constructor(

) : ModelMapper<RoomCategory, Category> {
    override fun map(from: RoomCategory): Category {
        return Category(from.id, from.name)
    }
}