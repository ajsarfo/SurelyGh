package com.sarftec.newsurelygh.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.newsurelygh.databinding.FragmentBaseBinding
import com.sarftec.newsurelygh.view.listener.BaseFragmentListener
import com.sarftec.newsurelygh.view.parcel.PostToDetail
import com.sarftec.newsurelygh.view.recycler.PostItemAdapter
import com.sarftec.newsurelygh.view.viewmodel.MainViewModel
import com.sarftec.surelygh.view.recycler.ItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class BaseFragment : Fragment() {

    private lateinit var layoutBinding: FragmentBaseBinding

    private val viewModel by viewModels<MainViewModel>()

    private val postItemAdapter by lazy {
        PostItemAdapter(lifecycleScope, viewModel) {
            fragmentListener.navigateToDetail(
                PostToDetail(it.id)
            )
        }
    }

    private lateinit var fragmentListener: BaseFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListener = context as BaseFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutBinding = FragmentBaseBinding.inflate(
            inflater,
            container,
            false
        )
        setupAdapter()
        viewModel.screenState.observe(viewLifecycleOwner) {
            observePosts(it)
        }
        //viewModel.fetchPosts(postCategory)
        arguments?.getParcelable<FragmentBundle>(BUNDLE_PARAM)?.let {
            viewModel.fetchPosts(it.toCategoryParam())
        }
        return layoutBinding.root
    }

    private fun observePosts(state: MainViewModel.ScreenState) {
        when (state) {
            is MainViewModel.ScreenState.Error -> Log.v("TAG", state.message)
            is MainViewModel.ScreenState.Result -> lifecycleScope.launch {
                state.source.collect { postItemAdapter.submitData(it) }
            }
            else -> {}
        }
    }

    fun setCategoryParam(param: MainViewModel.CategoryParam) {
       arguments = Bundle().also {
           it.putParcelable(BUNDLE_PARAM, param.toParcel())
       }
    }

    private fun setupAdapter() {
        layoutBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postItemAdapter
            addItemDecoration(ItemDecorator(8f, requireContext()))
        }
    }

    @Parcelize
    class FragmentBundle(val slug: String) : Parcelable

    fun MainViewModel.CategoryParam.toParcel(): FragmentBundle {
        return FragmentBundle(
            when (this) {
                is MainViewModel.CategoryParam.Other -> slug
                else -> ""
            }
        )
    }

    fun FragmentBundle.toCategoryParam() : MainViewModel.CategoryParam {
        return if(slug.isEmpty()) MainViewModel.CategoryParam.Latest
        else MainViewModel.CategoryParam.Other(slug)
    }

    companion object {
        const val BUNDLE_PARAM = "bundle_param"
    }
}