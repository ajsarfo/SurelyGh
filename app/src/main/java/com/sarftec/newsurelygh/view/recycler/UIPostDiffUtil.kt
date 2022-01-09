package com.sarftec.newsurelygh.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.sarftec.newsurelygh.view.model.UIPost

object UIPostDiffUtil : DiffUtil.ItemCallback<UIPost>() {

    override fun areItemsTheSame(oldItem: UIPost, newItem: UIPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UIPost, newItem: UIPost): Boolean {
      return oldItem == newItem
    }
}