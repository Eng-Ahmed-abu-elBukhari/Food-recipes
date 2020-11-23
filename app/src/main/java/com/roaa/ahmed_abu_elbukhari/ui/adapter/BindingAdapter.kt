package com.roaa.ahmed_abu_elbukhari.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.CategoryItem
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.MealCategoryObject
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.Details
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.MealObject
import com.google.android.material.appbar.CollapsingToolbarLayout


@BindingAdapter("android:loadCategoryImage")
fun setCategoryImage(view:ImageView,category: MealCategoryObject){
    category.categoryImage.let {
        Glide.with(view.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(view)
    }
}

@BindingAdapter("android:category_name")
fun setCategoryName(view: TextView,category: MealCategoryObject){
    category.categoryName.let {
        view.text = it
    }
}




@BindingAdapter("android:meal_image")
fun setMealImage(view : ImageView,lastMeal: MealObject){

    lastMeal.mealImage.let {
        Glide.with(view.context)
            .load(it)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(view)
    }

}


@BindingAdapter("android:meal_name")
fun setMealName(view: TextView,category: MealObject){
    category.mealName.let {
        view.text = it
    }
}



@BindingAdapter("android:meal_description")
fun setMealDescription(view: TextView,categoryItem: CategoryItem){

    categoryItem.category_description?.let {
        view.text = it
    }
}


@BindingAdapter("android:imageCategory")
fun setImageCategory(view: ImageView,categoryItem: CategoryItem){

    categoryItem.category_image_utl?.let {
        Glide.with(view.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(view)
    }
}



@BindingAdapter("android:meal_name_ByCategory")
fun setMealNameByCategory(view:TextView,mealCategoryObject: MealObject){
    mealCategoryObject.mealName.let {
        view.text = it
    }
}

@BindingAdapter("android:meal_image_ByCategory")
fun setMealImageByCategory(view:ImageView,mealCategoryObject: MealObject){
    mealCategoryObject.mealImage.let {
        Glide.with(view.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(view)
    }
}


@BindingAdapter("android:collapsingTitle")
fun setCollapsingTitle(view: CollapsingToolbarLayout,mealDetails: Details){
    mealDetails.meal_name.let {
        view.title = it
    }
}



