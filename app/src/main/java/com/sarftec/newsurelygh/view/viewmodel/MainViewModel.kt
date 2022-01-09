package com.sarftec.newsurelygh.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.domain.repository.CategoryRepository
import com.sarftec.newsurelygh.domain.repository.NewsRepository
import com.sarftec.newsurelygh.tools.Resource
import com.sarftec.newsurelygh.view.model.UIPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    suspend fun getPostImage(post: Post): Resource<Image> {
        return newsRepository.getPostImage(post.imageId)
    }

    fun fetchPosts(param: CategoryParam) {
        if (_screenState.value != null) return
        viewModelScope.launch {
          val result =  when (param) {
                is CategoryParam.Latest -> newsRepository.getLatestPosts().let {
                    if (it.isSuccess()) ScreenState.Result(it.data!!.toLatestUI())
                    else ScreenState.Error(it.message!!)
                }
                is CategoryParam.Other -> categoryRepository.findCategory(param.slug).let {
                    if (it.isSuccess()) newsRepository.getPostsForCategory(it.data!!.id)
                        .let { result ->
                            if (result.isSuccess()) ScreenState.Result(result.data!!.toUI())
                            else ScreenState.Error(result.message!!)
                        }
                    else ScreenState.Error(it.message!!)
                }
            }
            _screenState.value = result
        }
    }

    private fun Flow<PagingData<Post>>.toLatestUI(): Flow<PagingData<UIPost>> {
        return this.map { items ->
            items.map {
                UIPost.Model(it.copy(date = "Trending"))
            }
        }
    }

    private fun Flow<PagingData<Post>>.toUI(): Flow<PagingData<UIPost>> {
        return this.map { items ->
            items.map {
                UIPost.Model(it)
            }
        }
    }

    /**
     * List of UI classes for updating screen
     **/
    sealed class CategoryParam {
        class Other(val slug: String) : CategoryParam()
        object Latest : CategoryParam()
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Result(val source: Flow<PagingData<UIPost>>) : ScreenState()
        class Error(val message: String) : ScreenState()
    }
}