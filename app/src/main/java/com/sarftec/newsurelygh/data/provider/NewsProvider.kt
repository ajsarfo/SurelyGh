package com.sarftec.newsurelygh.data.provider

import com.sarftec.newsurelygh.data.service.NewsService
import com.sarftec.newsurelygh.data.service.mapper.CategoryMapper
import com.sarftec.newsurelygh.data.service.mapper.DetailMapper
import com.sarftec.newsurelygh.data.service.mapper.ImageMapper
import com.sarftec.newsurelygh.data.service.mapper.PostMapper
import com.sarftec.newsurelygh.domain.model.Category
import com.sarftec.newsurelygh.domain.model.Detail
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.tools.ModelMapper
import com.sarftec.newsurelygh.tools.Resource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsProvider @Inject constructor(
    private val newsService: NewsService,
    private val postMapper: PostMapper,
    private val detailMapper: DetailMapper,
    private val imageMapper: ImageMapper,
    private val categoryMapper: CategoryMapper
) {
    suspend fun getPostForCategory(
        categoryId: Int,
        pageSize: Int,
        page: Int
    ): Resource<List<Post>> {
        return mapToResource(
            retrieve = { newsService.getPostsForCategory(categoryId, pageSize, page) },
            mapper = { it.mapToDomain(postMapper) }
        )
    }

    suspend fun getLatestPosts(
        pageSize: Int,
        page: Int
    ): Resource<List<Post>> {
        return mapToResource(
            retrieve = { newsService.getLatestPosts(pageSize, page) },
            mapper = { it.mapToDomain(postMapper) }
        )
    }

    suspend fun getPostDetail(postId: Int): Resource<Detail> {
        return mapToResource(
            retrieve = { newsService.getPostDetail(postId) },
            mapper = { detailMapper.map(it) }
        )
    }

    suspend fun getPostImage(imageId: Int): Resource<Image> {
        return mapToResource(
            retrieve = { newsService.getPostImage(imageId) },
            mapper = { imageMapper.map(it) }
        )
    }

    suspend fun getCategories(): Resource<List<Category>> {
        return mapToResource(
            retrieve = { newsService.getCategories() },
            mapper = { it.mapToDomain(categoryMapper) }
        )
    }

    private suspend fun <T, U> mapToResource(
        retrieve: suspend () -> Response<T>,
        mapper: (T) -> U
    ): Resource<U> {
        return try {
            val response = retrieve()
            if (!response.isSuccessful) {
                return Resource.error("Error => ${response.errorBody()?.string()}")
            }
            Resource.success(mapper(response.body()!!))
        } catch (e: Exception) {
            Resource.error("Error => ${e.message}")
        }
    }

    private fun <T, U> List<T>.mapToDomain(mapper: ModelMapper<T, U>): List<U> {
        return map { mapper.map(it) }
    }
}