package com.sarftec.newsurelygh.view.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.newsurelygh.view.model.UIPost

abstract class PostBaseViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(viewGroup) {
    abstract fun bind(post: UIPost)
}