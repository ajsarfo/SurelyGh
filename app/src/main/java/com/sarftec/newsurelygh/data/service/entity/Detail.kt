package com.sarftec.newsurelygh.data.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Detail(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: Title,
    @SerialName("slug")
    val slug: String,
    @SerialName("content")
    val content: Content,
    @SerialName("featured_media")
    val imageId: Int
)

@Serializable
class Content(
    @SerialName("rendered")
    val rendered: String
)