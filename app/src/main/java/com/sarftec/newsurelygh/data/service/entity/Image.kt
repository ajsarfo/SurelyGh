package com.sarftec.newsurelygh.data.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class Image(
    @SerialName("source_url")
    val url: String,
    @SerialName("media_details")
    val details: Details
)

@Serializable
class Details(
    @SerialName("sizes")
    val sizes: Sizes
)

@Serializable
class Sizes(
    @SerialName("medium")
    val medium: Location,
    @SerialName("td_696x385")
    val large: Location
)

@Serializable
class Location(
    @SerialName("source_url")
    val url: String
)