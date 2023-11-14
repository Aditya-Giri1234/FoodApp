package com.aditya.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.utils.Constans


@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("select * from ${Constans.tableName}")
    fun getMealData():LiveData<List<Meal>>
}