package com.sarftec.newsurelygh.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarftec.newsurelygh.databinding.LayoutPostNativeAdBinding
import com.sarftec.newsurelygh.view.model.UIPost

class PostNativeAdViewHolder(
    private val layoutBinding: LayoutPostNativeAdBinding
) : PostBaseViewHolder(layoutBinding.root) {

    override fun bind(post: UIPost) {

    }

    companion object {
        fun getInstance(parent: ViewGroup) : PostNativeAdViewHolder {
            return PostNativeAdViewHolder(
                LayoutPostNativeAdBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}