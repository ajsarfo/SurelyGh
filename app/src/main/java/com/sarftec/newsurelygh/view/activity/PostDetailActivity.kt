package com.sarftec.newsurelygh.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarftec.newsurelygh.databinding.ActivityDetailBinding
import com.sarftec.newsurelygh.view.parcel.PostToDetail
import com.sarftec.newsurelygh.view.utils.parseFromUTF
import com.sarftec.newsurelygh.view.viewmodel.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailActivity : BaseActivity() {


    private val layoutBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<PostDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        setupToolbar()
        getParcelFromIntent<PostToDetail>(intent)?.let {
            viewModel.setParcel(it)
        }
        viewModel.fetchDetail()
        viewModel.screenState.observe(this) {
            observeDetail(it)
        }
    }

    private fun observeDetail(state: PostDetailViewModel.ScreenState) {
        when(state) {
            is PostDetailViewModel.ScreenState.Loading -> {
                layoutBinding.progress.visibility = View.VISIBLE
                layoutBinding.contentLayout.visibility = View.GONE
            }
            is PostDetailViewModel.ScreenState.Error -> {
                layoutBinding.progress.visibility = View.GONE
                Log.v("TAG", state.message)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is PostDetailViewModel.ScreenState.Result -> {
                layoutBinding.apply {
                    progress.visibility = View.GONE
                    contentLayout.visibility = View.VISIBLE
                    title.text = state.detail.title.parseFromUTF()
                    // slug.text = state.detail.slug
                    contentView.loadDataWithBaseURL(
                        "",
                        state.detail.content,
                        "text/html",
                        "UTF-8",
                        ""
                    )
                }
                lifecycleScope.launch {
                    viewModel.getDetailHeader(state.detail).let {
                        if(it.isSuccess())  Glide.with(this@PostDetailActivity)
                            .load(it.data!!.large)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(layoutBinding.headerImage)
                    }
                }
            }
        }
    }


    private fun setupToolbar() {
        setSupportActionBar(layoutBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        layoutBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}