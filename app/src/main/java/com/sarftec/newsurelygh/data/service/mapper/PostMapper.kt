package com.sarftec.newsurelygh.data.service.mapper

import com.sarftec.newsurelygh.data.service.entity.Post
import com.sarftec.newsurelygh.tools.ModelMapper
import java.text.SimpleDateFormat
import javax.inject.Inject

typealias DomainPost = com.sarftec.newsurelygh.domain.model.Post


class PostMapper @Inject constructor(): ModelMapper<Post, DomainPost> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val newFormat = SimpleDateFormat("MMM dd, mm:ss")

    fun formatDate(input: String): String {
        return newFormat.format(
            dateFormat.parse(input.replace("T", " "))!!
        ).toString()
    }

    override fun map(from: Post): DomainPost {
        return DomainPost(from.id, from.title.rendered, from.imageId, formatDate(from.date))
    }
}