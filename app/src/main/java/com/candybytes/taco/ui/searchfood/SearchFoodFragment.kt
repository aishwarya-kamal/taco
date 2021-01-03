package com.candybytes.taco.ui.searchfood

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentSearchFoodBinding
import com.candybytes.taco.ui.common.CategoryOrSearchFoodAdapter
import com.candybytes.taco.ui.util.FoodClickListener
import com.candybytes.taco.ui.util.clearFocusAndCollapseSearchView
import com.candybytes.taco.vo.Food
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFoodFragment : Fragment() {

    private lateinit var viewModel: SearchFoodViewModel
    private lateinit var binding: FragmentSearchFoodBinding
    private lateinit var searchView: SearchView
    private var currentWordToBeSearched: String? = null

    private val adapter = CategoryOrSearchFoodAdapter(FoodClickListener { food: Food ->
        clearFocusAndCollapseSearchView(view, searchView)

        this.findNavController().navigate(
            SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodFragment2(food)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_food,
            container, false
        )

        binding.lifecycleOwner = this

        binding.recyclerViewSearchFoodFragment.adapter = adapter

        viewModel = ViewModelProvider(this).get(SearchFoodViewModel::class.java)

        lifecycleScope.launch {
            viewModel.allFood.collectLatest {
                adapter.submitData(it)
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search_bar)
        searchView = search.actionView as SearchView
        searchView.queryHint = "Search food..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // Search food when user hits the search
            override fun onQueryTextSubmit(foodQuery: String?): Boolean {
                if (foodQuery != null) {
                    currentWordToBeSearched = foodQuery
                    searchFood(foodQuery)
                }
                return true
            }

            // Search food as query string changes
            override fun onQueryTextChange(foodQuery: String?): Boolean {
                if (foodQuery != null) {
                    currentWordToBeSearched = foodQuery
                    searchFood(foodQuery)
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            clearFocusAndCollapseSearchView(view, searchView)
            return@setOnCloseListener true
        }
    }

    // Search food based on the query string
    private fun searchFood(query: String) {

        lifecycleScope.launch {
            viewModel.getFilteredFoodList(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        currentWordToBeSearched?.let { searchFood(it) }
    }

}
