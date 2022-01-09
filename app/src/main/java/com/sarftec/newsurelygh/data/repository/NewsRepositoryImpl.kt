package com.sarftec.newsurelygh.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sarftec.newsurelygh.data.paging.PostPagingSource
import com.sarftec.newsurelygh.data.provider.NewsProvider
import com.sarftec.newsurelygh.domain.model.Detail
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.domain.repository.NewsRepository
import com.sarftec.newsurelygh.tools.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGE_SIZE = 10

class NewsRepositoryImpl @Inject constructor(
    private val newsProvider: NewsProvider,
) : NewsRepository {

    override fun getPostsForCategory(categoryId: Int): Resource<Flow<PagingData<Post>>> {
        return Resource.success(
            Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false)) {
                PostPagingSource { page ->
                    newsProvider.getPostForCategory(categoryId, PAGE_SIZE, page)
                }
            }.flow
        )
    }

    override fun getLatestPosts(): Resource<Flow<PagingData<Post>>> {
        return Resource.success(
            Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false)) {
                PostPagingSource { page ->
                    newsProvider.getLatestPosts(PAGE_SIZE, page)
                }
            }.flow
        )
    }

    override suspend fun getPostDetail(postId: Int): Resource<Detail> {
        return newsProvider.getPostDetail(postId)
    }

    override suspend fun getPostImage(imageId: Int): Resource<Image> {
        return newsProvider.getPostImage(imageId)
    }
}

