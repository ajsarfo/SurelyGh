package com.sarftec.newsurelygh.data.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: Title,
    @SerialName("featured_media")
    val imageId: Int,
    @SerialName("date")
    val date: String
)

@Serializable
data class Title(
    @SerialName("rendered")
    val rendered: String
)

@Serializable
data class Embedded(
    @SerialName("wp:featuredmedia")
    val featuredMedia: List<FeaturedMedia>,
)

@Serializable
data class FeaturedMedia(
    @SerialName("source_url")
    val sourceURL: String,
)