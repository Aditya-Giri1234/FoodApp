package com.aditya.foodapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.utils.Constans


@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase:RoomDatabase() {
    abstract fun getMealDao():MealDao

    companion object{
        @Volatile
        private var INSTANCE:MealDatabase?=null

        fun getDatabase(context: Context):MealDatabase{
            if(INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context,MealDatabase::class.java, Constans.dbName).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}

