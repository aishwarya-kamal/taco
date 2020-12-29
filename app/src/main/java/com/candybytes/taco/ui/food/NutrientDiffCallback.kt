package com.candybytes.taco.ui.food

import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.vo.Nutrient

class NutrientDiffCallback : DiffUtil.ItemCallback<Nutrient>() {
    override fun areItemsTheSame(oldItem: Nutrient, newItem: Nutrient): Boolean {
        return oldItem.qty == newItem.qty
    }

    override fun areContentsTheSame(oldItem: Nutrient, newItem: Nutrient): Boolean {
        return oldItem == newItem
    }
}