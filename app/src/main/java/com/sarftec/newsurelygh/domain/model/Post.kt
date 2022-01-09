package com.sarftec.newsurelygh.domain.model

data class Post(
    val id: Int,
    val title: String,
    val imageId: Int,
    val date: String
) {
    override fun equals(other: Any?): Boolean {
        if(other !is Post) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + imageId
        result = 31 * result + date.hashCode()
        return result
    }
}