package com.sarftec.newsurelygh.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sarftec.newsurelygh.R
import com.sarftec.newsurelygh.view.viewmodel.MainViewModel.CategoryParam

class FragmentPager(private val activity: FragmentActivity)
    : FragmentStateAdapter(activity) {

    private val sections by lazy {
        activity.resources.getStringArray(R.array.news_categories)
    }

    override fun getItemCount(): Int = sections.size

    override fun createFragment(position: Int): Fragment {
        val fragment = BaseFragment()
        when(position) {
            0 -> fragment.setCategoryParam(CategoryParam.Latest)
            else -> fragment.setCategoryParam(CategoryParam.Other(sections[position]))
        }
        return fragment
    }
}