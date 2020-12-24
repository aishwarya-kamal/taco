package com.candybytes.taco.ui.searchfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemFoodBinding
import com.candybytes.taco.ui.util.FoodClickListener
import com.candybytes.taco.vo.Food

class SearchFoodAdapter(private val foodClickListener: FoodClickListener) :
    ListAdapter<Food, RecyclerView.ViewHolder>(SearchFoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodListViewHolder(
            ItemFoodBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FoodListViewHolder).bind(foodClickListener, getItem(position))
    }


    class FoodListViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: FoodClickListener, item: Food) {
            binding.apply {
                foodClickListener = listener
                food = item
                executePendingBindings()
            }
        }
    }
}
