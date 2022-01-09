package com.sarftec.newsurelygh.data.service.mapper

import android.net.Uri
import com.sarftec.newsurelygh.data.service.entity.Image
import com.sarftec.newsurelygh.tools.ModelMapper
import javax.inject.Inject

typealias DomainImage = com.sarftec.newsurelygh.domain.model.Image

class ImageMapper @Inject constructor(): ModelMapper<Image, DomainImage> {
    override fun map(from: Image): DomainImage {
        return DomainImage(
            Uri.parse(from.details.sizes.medium.url),
            Uri.parse(from.details.sizes.large.url)
        )
    }
}