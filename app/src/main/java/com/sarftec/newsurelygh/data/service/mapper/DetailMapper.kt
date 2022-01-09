package com.sarftec.newsurelygh.data.service.mapper

import com.sarftec.newsurelygh.data.service.entity.Detail
import com.sarftec.newsurelygh.tools.ModelMapper
import javax.inject.Inject

typealias DomainDetail = com.sarftec.newsurelygh.domain.model.Detail

class DetailMapper @Inject constructor(): ModelMapper<Detail, DomainDetail> {
    override fun map(from: Detail): DomainDetail {
        return DomainDetail(
            from.id,
            from.imageId,
            from.title.rendered,
            from.slug,
            from.content.rendered
        )
    }
}