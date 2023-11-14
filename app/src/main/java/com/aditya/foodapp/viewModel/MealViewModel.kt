package com.aditya.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.foodapp.db.MealDatabase
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.model.meal.MealList
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.aditya.foodapp.model.popularMeal.PopularMealList
import com.aditya.foodapp.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(private  val mealDatabase: MealDatabase) : ViewModel() {
    private val mealData=MutableLiveData<Meal>()
    private val TAG="MealViewModel"

    fun getPicDetailsById(id :String){
        RetrofitInstance.api.getPicDetailsById(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        val meal=it.meals[0]
                        mealData.value=meal
                    }
                }
                else{
                    Log.e("FoodApi","$TAG - getPicDetailsById  - onResponse - Api call failed - response->$response")
                    return
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.e("FoodApi","$TAG - getPicDetailsById - onFailure - Api call failed - response->${t.message}")
            }
        })
    }

    fun observeMealData(): LiveData<Meal> {
        return mealData
    }

    fun upsert(meal:Meal)=viewModelScope.launch {
        mealDatabase.getMealDao().upsert(meal)
    }

    fun delete(meal:Meal)=viewModelScope.launch {
        mealDatabase.getMealDao().delete(meal)
    }

}