package com.roaa.ahmed_abu_elbukhari.internet

import com.roaa.ahmed_abu_elbukhari.data.model.meal_details.MealDetails
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.MealCategories
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.Meals
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://www.themealdb.com/api/json/v1/1/categories.php


// TODO 1- BASE URL
private const val BASE_URL = "https://www.themealdb.com/"
        //"api/json/v1/1/"



// TODO 2- moshi builder
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// TODO 3- Use Retrofit.Builder to create the Retrofit object.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()



// TODO 4- implement interface
interface MealApiService {
    @GET("api/json/v1/1/categories.php")
     fun getCategoriesAsync():
            Deferred<MealCategories>


    @GET("api/json/v1/1/filter.php")
     fun getMealsByAreaAsync(@Query("a") area:String):
            Deferred<Meals>


    // https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("api/json/v1/1/filter.php")
    fun getMealByCategoryAsync(@Query("c") category:String):
        Deferred<Meals>



    @GET("api/json/v1/1/search.php")
    fun getMealDetailsByNameAsync(@Query("s") mealName:String):
            Deferred<MealDetails>



    @GET("api/json/v1/1/search.php")
    fun getMealByFirstLatterAsync(@Query("f") letter:String ):
            Deferred<MealDetails>
}


object MealApi {
    val retrofitService : MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}