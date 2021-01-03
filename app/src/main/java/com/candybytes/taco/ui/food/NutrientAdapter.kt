package com.candybytes.taco.ui.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemFoodDetailBinding
import com.candybytes.taco.vo.Nutrient

class NutrientAdapter(var nutrientKeys: List<String>): ListAdapter<Nutrient,
        RecyclerView.ViewHolder>(NutrientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NutrientListViewHolder(
            ItemFoodDetailBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NutrientListViewHolder).bind(getItem(position), nutrientKeys[position])
    }


    class NutrientListViewHolder(private val binding: ItemFoodDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Nutrient, nutrientKey: String) {
            binding.apply {
                nutrientName = nutrientKey
                nutrient = item
                executePendingBindings()
            }
        }
    }
}
