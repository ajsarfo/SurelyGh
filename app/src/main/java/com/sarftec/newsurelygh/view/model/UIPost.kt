package com.sarftec.newsurelygh.view.model

import com.sarftec.newsurelygh.domain.model.Post

sealed class UIPost {
    class Model(val post: Post) : UIPost()
    object NativeAd : UIPost()
}