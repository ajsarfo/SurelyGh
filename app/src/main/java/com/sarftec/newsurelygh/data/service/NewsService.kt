package com.sarftec.newsurelygh.data.service

import com.sarftec.newsurelygh.data.service.entity.Category
import com.sarftec.newsurelygh.data.service.entity.Detail
import com.sarftec.newsurelygh.data.service.entity.Image
import com.sarftec.newsurelygh.data.service.entity.Post
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("posts")
    suspend fun getPostsForCategory(
        @Query("categories") categoryId: Int,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ) : Response<List<Post>>

    @GET("posts")
    suspend fun getLatestPosts(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ) : Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostDetail(@Path("id") id: Int) : Response<Detail>

    @GET("media/{id}")
    suspend fun getPostImage(@Path("id") id: Int) : Response<Image>

    companion object {
        const val NEWS_BASE_URL = "https://surelygh.com/wp-json/wp/v2/"

        fun create(retrofit: Retrofit) : NewsService {
            return retrofit.create(NewsService::class.java)
        }
    }
}