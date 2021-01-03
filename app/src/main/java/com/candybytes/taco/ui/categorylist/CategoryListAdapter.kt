package com.candybytes.taco.ui.categorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemCategoryBinding
import com.candybytes.taco.ui.util.ClickListener
import com.candybytes.taco.vo.Category

/*
* CategoryListAdapter presents list data in recyclerview which include calculating diffs
* between the lists on background thread.
* CategoryListAdapter creates the CategoryListViewHolder in onCreateViewHolder and binds
* it in onBindViewHolder.
* */
class CategoryListAdapter(private val clickListener: ClickListener) :
    ListAdapter<Category, RecyclerView.ViewHolder>(CategoryListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryListViewHolder(
            ItemCategoryBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryListViewHolder).bind(clickListener, getItem(position))
    }


    class CategoryListViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // The CategoryListViewHolder class allows to bind category item to its view
        fun bind(listener: ClickListener, item: Category) {
            binding.apply {
                clickListener = listener
                category = item
                executePendingBindings()
            }
        }
    }
}
