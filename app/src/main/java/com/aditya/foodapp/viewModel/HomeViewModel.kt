package com.aditya.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.foodapp.db.MealDatabase
import com.aditya.foodapp.model.category.CategoryList
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.model.meal.MealList
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.aditya.foodapp.model.popularMeal.PopularMealList
import com.aditya.foodapp.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private val randomMeal = MutableLiveData<Meal>()
    private val popularMealData = MutableLiveData<PopularMealList>()
    private val categoryData = MutableLiveData<CategoryList>()
    private val favouriteMeal = mealDatabase.getMealDao().getMealData()
    private val bottomSheetMeal=MutableLiveData<Meal>()
    private val searchMeal=MutableLiveData<MealList>()
    private val TAG = "HomeViewModel"

    private var savedRandomMeal:Meal?=null

    fun getRandomMeal() {
        savedRandomMeal?.let {
            randomMeal.postValue(it)
            return
        }
        RetrofitInstance.api.getRandomPic().enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val meal = it.meals[0]
                        randomMeal.value = meal
                        savedRandomMeal=meal
                    }
                } else {
                    Log.e(
                        "FoodApi",
                        "$TAG - getRandomMeal  - onResponse - Api call failed - response->$response"
                    )
                    return
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.e(
                    "FoodApi",
                    "$TAG - getRandomMeal - onFailure - Api call failed - response->${t.message}"
                )
            }
        })
    }

    fun getPopularMeal(category: String) {
        RetrofitInstance.api.getPopularMeal(category).enqueue(object : Callback<PopularMealList?> {
            override fun onResponse(
                call: Call<PopularMealList?>,
                response: Response<PopularMealList?>
            ) {
                if (response.isSuccessful) {

                    response.body()?.let {
                        val meal = it
                        popularMealData.value = meal
                    }
                } else {
                    Log.e(
                        "FoodApi",
                        "$TAG - getPopularMeal  - onResponse - Api call failed - response->$response"
                    )
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealList?>, t: Throwable) {
                Log.e(
                    "FoodApi",
                    "$TAG - getPopularMeal - onFailure - Api call failed - response->${t.message}"
                )
            }
        })
    }

    fun getCategory() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList?> {
            override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {
                if (response.isSuccessful) {

                    response.body()?.let {
                        val meal = it
                        categoryData.value = meal
                    }
                } else {
                    Log.e(
                        "FoodApi",
                        "$TAG - getCategory  - onResponse - Api call failed - response->$response"
                    )
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
                Log.e(
                    "FoodApi",
                    "$TAG - getCategory - onFailure - Api call failed - response->${t.message}"
                )
            }
        })
    }

    fun getMealById(id: String) = viewModelScope.launch {
        RetrofitInstance.api.getPicDetailsById(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                val response=response.body()?.meals?.first()
                response?.let {
                    bottomSheetMeal.postValue(it)
                }


            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.e("MealById", "$TAG - getMealById - error - messgae-.${t.message}")
            }

        })
    }

    fun getSearchMeal(query:String)=viewModelScope.launch {
        RetrofitInstance.api.getSearchMeal(query).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                response.body()?.let {
                    searchMeal.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
               Log.e("SearchMealApi","$TAG - getSearchMeal - error -message->${t.message}")
            }
        })
    }

    //    fun getFavouriteMealList(){
//        favouriteMeal.postValue(mealDatabase.getMealDao().getMealData().value)
//    }
    fun upsert(meal: Meal) = viewModelScope.launch {
        mealDatabase.getMealDao().upsert(meal)
    }

    fun delete(meal: Meal) = viewModelScope.launch {
        mealDatabase.getMealDao().delete(meal)
    }

    fun observeRandomMealData(): LiveData<Meal> {
        return randomMeal
    }

    fun observePopularMealData(): LiveData<PopularMealList> {
        return popularMealData
    }

    fun observeCategoryData(): LiveData<CategoryList> {
        return categoryData
    }

    fun observeFavouriteList(): LiveData<List<Meal>> {
        return favouriteMeal
    }

    fun observeBottomSheetMeal():LiveData<Meal>{
        return bottomSheetMeal
    }

    fun observeSearchMeal():LiveData<MealList>{
        return searchMeal
    }
}