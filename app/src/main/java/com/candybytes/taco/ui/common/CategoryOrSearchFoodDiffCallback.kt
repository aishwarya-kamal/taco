package com.candybytes.taco.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.vo.Food

class CategoryOrSearchFoodDiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}