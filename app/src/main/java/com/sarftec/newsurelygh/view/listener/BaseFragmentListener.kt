package com.sarftec.newsurelygh.view.listener

import com.sarftec.newsurelygh.view.parcel.PostToDetail

interface BaseFragmentListener {
    fun navigateToDetail(parcel: PostToDetail)
}