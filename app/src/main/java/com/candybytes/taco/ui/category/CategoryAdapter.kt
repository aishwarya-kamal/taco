package com.candybytes.taco.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemFoodBinding
import com.candybytes.taco.ui.util.FoodClickListener
import com.candybytes.taco.vo.Food

class CategoryAdapter(private val foodClickListener: FoodClickListener) :
    PagingDataAdapter<Food, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodListViewHolder(
            ItemFoodBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FoodListViewHolder).bind(foodClickListener, it) }
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
