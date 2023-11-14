package com.aditya.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.foodapp.model.popularMeal.PopularMealList
import com.aditya.foodapp.utils.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel(){
    private val categoryData= MutableLiveData<PopularMealList>()
    val TAG="MealCategoryViewModel"


    fun getMealByCategory(category:String){
        RetrofitInstance.api.getMealByCategory(category).enqueue(object : Callback<PopularMealList?> {
            override fun onResponse(call: Call<PopularMealList?>, response: Response<PopularMealList?>) {
                if(response.isSuccessful){

                    response.body()?.let {
                        val meal=it
                        categoryData.value=meal
                    }
                }
                else{
                    Log.e("FoodApi","$TAG - getMealByCategory  - onResponse - Api call failed - response->$response")
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealList?>, t: Throwable) {
                Log.e("FoodApi","$TAG - getMealByCategory - onFailure - Api call failed - response->${t.message}")
            }
        })
    }

    fun observeCategoryMealData(): LiveData<PopularMealList> {
        return categoryData
    }
}