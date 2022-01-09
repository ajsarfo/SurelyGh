package com.sarftec.newsurelygh.view.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarftec.newsurelygh.R
import com.sarftec.newsurelygh.databinding.LayoutPostItemBinding
import com.sarftec.newsurelygh.domain.model.Image
import com.sarftec.newsurelygh.domain.model.Post
import com.sarftec.newsurelygh.tools.Resource
import com.sarftec.newsurelygh.view.model.UIPost
import com.sarftec.newsurelygh.view.task.Task
import com.sarftec.newsurelygh.view.task.TaskManager
import com.sarftec.newsurelygh.view.utils.parseFromUTF
import com.sarftec.newsurelygh.view.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class PostItemViewHolder private constructor(
    private val layoutBinding: LayoutPostItemBinding,
    private val dependency: ViewHolderDependency
) : PostBaseViewHolder(layoutBinding.root) {

    private val uuid = UUID.randomUUID().toString()

    private fun clearLayout(post: UIPost.Model) {
        layoutBinding.headingTitle.text = post.post.title.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ENGLISH
            ) else it.toString()
        }.parseFromUTF()
        layoutBinding.card.setOnClickListener { dependency.onClick(post.post) }
        layoutBinding.headingImage.setImageBitmap(null)
        layoutBinding.date.text = post.post.date
    }

    private fun setLayout(post: UIPost.Model, resource: Resource<Image>) {
        if (resource.isError()) {
            Log.v("TAG", resource.message!!)
            dependency.taskManager.removeTask(uuid)
            return
        }
        Glide.with(itemView)
            .load(resource.data!!.small)
            .placeholder(R.drawable.ic_logo)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(layoutBinding.headingImage)
        dependency.taskManager.removeTask(uuid)
    }

    override fun bind(post: UIPost) {
        if (post !is UIPost.Model) return
        clearLayout(post)
        val task = Task.createTask<UIPost.Model, Resource<Image>>(
            dependency.coroutineScope,
            post
        )
        task.addExecution { input -> dependency.viewModel.getPostImage(input.post) }
        task.addCallback { setLayout(post, it) }
        dependency.taskManager.addTask(uuid, task.build())
    }

    companion object {
        fun getInstance(parent: ViewGroup, dependency: ViewHolderDependency): PostItemViewHolder {
            return PostItemViewHolder(
                LayoutPostItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dependency
            )
        }
    }

    class ViewHolderDependency(
        val coroutineScope: CoroutineScope,
        val viewModel: MainViewModel,
        val taskManager: TaskManager<UIPost.Model, Resource<Image>>,
        val onClick: (Post) -> Unit
    )
}