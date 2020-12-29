package com.candybytes.taco.ui.util

import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food

class ClickListener(val clickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}


class FoodClickListener(val clickListener: (food: Food) -> Unit) {
    fun onClick(food: Food) = clickListener(food)
}
