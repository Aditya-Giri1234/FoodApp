package com.aditya.foodapp.utils

import com.aditya.foodapp.api.FoodApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    val api:FoodApi by lazy {
        Retrofit.Builder().baseUrl(Constans.base_url).addConverterFactory(GsonConverterFactory.create()).build().create(FoodApi::class.java)
    }

}