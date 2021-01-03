package com.candybytes.taco.ui.categorylist

import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.vo.Category

/*
* A utility class that calculates the difference between 2 lists on background thread
* It is used to know the updates for a RecyclerView Adapter by passing the old & new list.
*(no more calling notifyDataSetChanged())
* */
class CategoryListDiffCallback : DiffUtil.ItemCallback<Category>() {

    // Checks if 2 objects are the same (meaning do they represent same item)
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    // Checks if the data in the 2 items are the same
    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}