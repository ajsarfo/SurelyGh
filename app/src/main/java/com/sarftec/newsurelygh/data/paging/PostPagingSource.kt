package com.sarftec.newsurelygh.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.tools.Resource
import javax.inject.Inject

class PostPagingSource @Inject constructor(
    private val postRetriever: suspend (Int) -> Resource<List<Post>>
) : PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val currentPage = params.key ?: 1
        val result = postRetriever(currentPage).also {
            if(it.isError()) return LoadResult.Error(Exception(it.message!!))
        }
        return LoadResult.Page(
            nextKey = if(result.data!!.isEmpty()) null else currentPage + 1,
            prevKey = if(currentPage == 1) null else currentPage - 1,
            data = result.data
        )
    }
}