package com.aditya.foodapp.api

import com.aditya.foodapp.model.category.CategoryList
import com.aditya.foodapp.model.meal.MealList
import com.aditya.foodapp.model.popularMeal.PopularMealList
import com.aditya.foodapp.utils.Constans
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET(Constans.randomPic)
    fun getRandomPic(): Call<MealList>

    @GET(Constans.lookup)
    fun getPicDetailsById(@Query("i") i:String):Call<MealList>
    @GET(Constans.filter)
    fun getPopularMeal(@Query("c") category:String):Call<PopularMealList>


    @GET(Constans.category)
    fun getCategory(): Call<CategoryList>

    @GET(Constans.filter)
    fun getMealByCategory(@Query("c") category:String):Call<PopularMealList>

    @GET(Constans.search)
    fun getSearchMeal(@Query("s") query:String):Call<MealList>


}