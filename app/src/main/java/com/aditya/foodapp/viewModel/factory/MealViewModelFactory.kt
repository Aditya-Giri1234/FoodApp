package com.aditya.foodapp.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aditya.foodapp.db.MealDatabase
import com.aditya.foodapp.viewModel.MealViewModel

class MealViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}