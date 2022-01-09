package com.sarftec.newsurelygh.view.recycler

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.tools.Resource
import com.sarftec.newsurelygh.view.model.UIPost
import com.sarftec.newsurelygh.view.task.TaskManager
import com.sarftec.newsurelygh.view.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope

class PostItemAdapter(
    coroutineScope: CoroutineScope,
    viewModel: MainViewModel,
    onClick: (Post) -> Unit
) : PagingDataAdapter<UIPost, PostBaseViewHolder>(UIPostDiffUtil) {

    private val dependency = PostItemViewHolder.ViewHolderDependency(
        coroutineScope,
        viewModel,
        TaskManager(),
        onClick
    )

    override fun onBindViewHolder(holder: PostBaseViewHolder, position: Int) {
      getItem(position)?.let {
          holder.bind(it)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostBaseViewHolder {
       return when(viewType) {
           MODEL -> PostItemViewHolder.getInstance(parent, dependency)
           else -> PostNativeAdViewHolder.getInstance(parent)
       }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)
            ?.let { if(it is UIPost.Model) MODEL else SEPARATOR }
            ?: MODEL
    }

    companion object {
        const val SEPARATOR = 0
        const val MODEL = 1
    }
}