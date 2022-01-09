package com.sarftec.newsurelygh.view.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.sarftec.newsurelygh.R
import com.sarftec.newsurelygh.databinding.ActivityMainBinding
import com.sarftec.newsurelygh.view.fragment.FragmentPager
import com.sarftec.newsurelygh.view.listener.BaseFragmentListener
import com.sarftec.newsurelygh.view.parcel.PostToDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), BaseFragmentListener {


    private val layoutBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        setupViewPager()
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val tabHeadings = resources.getStringArray(R.array.news_categories)
        TabLayoutMediator(
            layoutBinding.tabLayout,
            layoutBinding.viewPager
        ) { tab, position ->
            tab.text = tabHeadings[position]
        }.attach()
    }

    private fun setupViewPager() {
        layoutBinding.viewPager.adapter = FragmentPager(this)
    }

    override fun navigateToDetail(parcel: PostToDetail) {
       navigateToWithParcel(PostDetailActivity::class.java, parcel = parcel)
    }
}