package com.candybytes.taco.ui.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentCategoryListBinding
import com.candybytes.taco.ui.util.ClickListener
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

        viewModel.getCategoryList.observe(viewLifecycleOwner, {
            Timber.d("** Category List size ${it.size}")
            adapter.submitList(it)
        })

        return binding.root
    }
}
