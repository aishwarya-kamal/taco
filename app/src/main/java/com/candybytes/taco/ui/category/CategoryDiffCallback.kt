package com.candybytes.taco.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food

class CategoryDiffCallback: DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}