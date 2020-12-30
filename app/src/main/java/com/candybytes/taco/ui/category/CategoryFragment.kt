package com.candybytes.taco.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentCategoryBinding
import com.candybytes.taco.ui.common.CategoryOrSearchFoodAdapter
import com.candybytes.taco.ui.util.FoodClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: FragmentCategoryBinding
    private val adapter = CategoryOrSearchFoodAdapter(FoodClickListener {
        this.findNavController().navigate(
            CategoryFragmentDirections.actionCategoryFragmentToFoodFragment(it)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category,
            container, false
        )

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        val args = CategoryFragmentArgs.fromBundle(requireArguments())

        binding.recyclerViewCategoryFragment.adapter = adapter

        lifecycleScope.launch {
            viewModel.getCategoryFoodList(args.category.id).collectLatest {
                adapter.submitData(it)
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = args.category.name

        return binding.root
    }

}