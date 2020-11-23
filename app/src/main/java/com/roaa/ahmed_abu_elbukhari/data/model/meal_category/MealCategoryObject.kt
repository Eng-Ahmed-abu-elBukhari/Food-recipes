package com.roaa.ahmed_abu_elbukhari.data.model.meal_category

import com.squareup.moshi.Json

data class MealCategoryObject(
    val idCategory: String,
    @Json(name = "strCategory")
    val categoryName: String,
    @Json(name = "strCategoryThumb")
    val categoryImage: String,
    val strCategoryDescription: String
)