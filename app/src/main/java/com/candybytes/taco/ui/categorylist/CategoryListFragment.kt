package com.candybytes.taco.ui.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentCategoryListBinding
import com.candybytes.taco.ui.util.ClickListener
import com.candybytes.taco.ui.util.Resource
import com.candybytes.taco.vo.Category
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private lateinit var viewModel: CategoryListViewModel
    private lateinit var binding: FragmentCategoryListBinding

    private val adapter = CategoryListAdapter(ClickListener { category ->
        this.findNavController().navigate(
            CategoryListFragmentDirections.actionCategoryListFragmentToCategoryFragment(category)
        )
    })

    private val observer = Observer<Resource<List<Category>>> {
        when (it.status) {
            Resource.Status.SUCCESS -> showCategoryList(it.data)
            Resource.Status.ERROR -> showError(it.message!!)
            Resource.Status.LOADING -> showLoading()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category_list,
            container, false
        )

        binding.lifecycleOwner = this

        binding.recyclerViewCategoryListFragment.adapter = adapter

        viewModel = ViewModelProvider(this).get(CategoryListViewModel::class.java)

        viewModel.getCategoryList.observe(viewLifecycleOwner, observer)

        return binding.root
    }


    private fun showCategoryList(categoryList: List<Category>?) {
        indicators(View.VISIBLE, View.GONE, View.GONE)
        adapter.submitList(categoryList)
    }

    private fun showLoading() {
        indicators(View.GONE, View.VISIBLE, View.GONE)
    }

    private fun showError(message: String) {
        indicators(View.GONE, View.GONE, View.VISIBLE)
        Timber.d("*** Error message is - $message")
    }

    private fun indicators(
        recyclerViewVisibility: Int, progressBarVisibility: Int,
        textViewNoDataVisibility: Int
    ) {
        binding.recyclerViewCategoryListFragment.visibility = recyclerViewVisibility
        binding.progressBarCategoryListFragment.visibility = progressBarVisibility
        binding.textViewNoDataCategoryListFragment.visibility = textViewNoDataVisibility
    }

}
