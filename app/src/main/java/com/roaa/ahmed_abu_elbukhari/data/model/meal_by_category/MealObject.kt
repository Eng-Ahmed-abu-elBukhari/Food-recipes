package com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category

import com.squareup.moshi.Json

data class MealObject(
    @Json(name = "strMeal")
    val mealName: String,
    @Json(name = "strMealThumb")
    val mealImage: String,
    val idMeal: String
)
