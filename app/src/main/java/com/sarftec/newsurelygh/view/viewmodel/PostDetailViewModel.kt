package com.sarftec.newsurelygh.view.viewmodel

import androidx.lifecycle.*
import com.sarftec.newsurelygh.domain.model.Detail
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.repository.NewsRepository
import com.sarftec.newsurelygh.tools.Resource
import com.sarftec.newsurelygh.view.parcel.PostToDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    fun fetchDetail() {
        _screenState.value = ScreenState.Loading
        val parcel = stateHandle.get<PostToDetail>(PARCEL) ?: let {
            _screenState.value = ScreenState.Error("Error => detail parcel not found!")
            return
        }
        viewModelScope.launch {
            newsRepository.getPostDetail(parcel.postId).let {
                _screenState.value = if(it.isSuccess()) ScreenState.Result(it.data!!)
                else ScreenState.Error(it.message!!)
            }
        }
    }

    suspend fun getDetailHeader(detail: Detail) : Resource<Image> {
        return newsRepository.getPostImage(detail.imageId)
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Result(val detail: Detail) : ScreenState()
        class Error(val message: String) : ScreenState()
    }

    fun setParcel(parcel: PostToDetail) {
        stateHandle.set(PARCEL, parcel)
    }

    companion object {
        const val PARCEL = "parcel"
    }
}