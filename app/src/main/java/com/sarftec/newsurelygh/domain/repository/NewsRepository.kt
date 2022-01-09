package com.sarftec.newsurelygh.domain.repository

import androidx.paging.PagingData
import com.sarftec.newsurelygh.domain.model.Detail
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.tools.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getPostsForCategory(categoryId: Int) : Resource<Flow<PagingData<Post>>>
    fun getLatestPosts() : Resource<Flow<PagingData<Post>>>
    suspend fun getPostDetail(postId: Int) : Resource<Detail>
    suspend fun getPostImage(imageId: Int) : Resource<Image>
}