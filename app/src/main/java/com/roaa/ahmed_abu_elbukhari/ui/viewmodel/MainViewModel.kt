package com.roaa.ahmed_abu_elbukhari.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.MealCategoryObject
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.MealObject
import com.roaa.ahmed_abu_elbukhari.data.utils.Constant
import com.roaa.ahmed_abu_elbukhari.internet.MealApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    private var _mealCategory = MutableLiveData<List<MealCategoryObject>>()
    val mealCategory:LiveData<List<MealCategoryObject>>
        get() = _mealCategory

    private var _meals = MutableLiveData<List<MealObject>>()
    val meals:LiveData<List<MealObject>>
        get() = _meals


    private var _mealByCategory = MutableLiveData<List<MealObject>>()
    val mealByCategory:LiveData<List<MealObject>>
     get() = _mealByCategory


    private var _mealDetailsByLetter = MutableLiveData<MutableList<MealObject>>()
    val mealDetailsByLetter:LiveData<MutableList<MealObject>>
    get() = _mealDetailsByLetter

  private var _mealDetails = MutableLiveData<Map<String, String?>>()
    val mealDetails:LiveData<Map<String, String?>>
    get() = _mealDetails



    override val coroutineContext: CoroutineContext
        get()  = Dispatchers.IO+ job

    private var job: Job = Job()



    private val pref by lazy {
        application.getSharedPreferences(Constant.AREA_SHARED_PREFERENCE_KEY,0)
    }



     fun initializationMealInfo() {
        initialSearchMealArea()
         getMealsCategories()
    }


    private fun initialSearchMealArea(){
        val area: String? = if (pref.getString(Constant.AREA_VALUE,"Italian") == null){
            "Italian"
        }else{
            pref.getString(Constant.AREA_VALUE,"Italian")
        }
        getMealsByArea(area!!)
    }



    fun updateMealSearchArea(newArea: String){
        if (pref.getString(Constant.AREA_VALUE,"Italian") != newArea){
            getMealsByArea(newArea)
        }
    }



    private fun getMealsCategories(){
        launch {
            val mealsCategories = MealApi
                .retrofitService.getCategoriesAsync()
            try {
                withContext (Dispatchers.Main){
                    val listResult = mealsCategories.await()
                    _mealCategory.value = listResult.categories
                }
            } catch (e: Exception) {

            }
        }
    }




    private fun getMealsByArea(area:String){
        launch {
            val mealResult = MealApi.retrofitService.getMealsByAreaAsync(area)
            try {
                withContext (Dispatchers.Main){
                    val listResult = mealResult.await()
                    _meals.value = listResult.meals
                }
            }catch (e:Exception){

            }
        }
    }



    fun getMealsByCategory(category:String){
        launch {
            val mealByCategory = MealApi.retrofitService
                .getMealByCategoryAsync(category)

            try {
                withContext(Dispatchers.Main){
                    val listResult = mealByCategory.await()
                    _mealByCategory.value = listResult.meals
                }
            }catch (e:java.lang.Exception){

            }
        }
    }




    fun getMealDetailsByName(meal_name:String){
        launch {
            val mealDetails = MealApi.retrofitService
                .getMealDetailsByNameAsync(meal_name)
            try {
                withContext(Dispatchers.Main){
                    val listResult = mealDetails.await()
                    _mealDetails.value = listResult.meals!![0]
                }
            }catch (e:java.lang.Exception){

            }
        }
    }

    fun getMealByLetter(letter:String){
        launch {
            val meals = MealApi.retrofitService.
            getMealByFirstLatterAsync(letter)

            val mealList = mutableListOf<MealObject>()
            try {
                withContext(Dispatchers.Main) {
                    val listResult = meals.await()
                    listResult.meals?.forEach {
                        val mealName = it["strMeal"] ?: error("")
                        val mealImage = it["strMealThumb"] ?: error("")
                        val idMeal = it["idMeal"] ?: error("")
                        mealList.add(MealObject(mealName,mealImage,idMeal))
                    }
                    _mealDetailsByLetter.value = mealList
                }
            } catch (e:Exception) {

            }
        }
    }





    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}