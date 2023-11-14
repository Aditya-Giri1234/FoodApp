package com.aditya.foodapp.api

import com.aditya.foodapp.model.meal.Meal

interface FavouriteCallback {
    fun onUndo()
    fun onDismiss(meal: Meal)
}