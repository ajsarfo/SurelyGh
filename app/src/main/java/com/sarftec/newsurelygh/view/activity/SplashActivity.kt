package com.sarftec.newsurelygh.view.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sarftec.newsurelygh.data.local.LocalAssets
import com.sarftec.newsurelygh.data.provider.CategoryProvider
import com.sarftec.newsurelygh.databinding.ActivitySplashBinding
import com.sarftec.newsurelygh.domain.repository.CategoryRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var imageAssets: LocalAssets

    private val layoutBinding by lazy {
        ActivitySplashBinding.inflate(
            layoutInflater
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        Glide.with(this)
            .load(imageAssets.getLogo())
            .into(layoutBinding.splashLogo)
        lifecycleScope.launchWhenCreated {
          categoryRepository.loadCategories().let {
                if(it.isError()) Log.v("TAG", it.message!!)
            }
            delay(TimeUnit.SECONDS.toMillis(3))
            navigateTo(MainActivity::class.java, finish = true)
        }
    }
}