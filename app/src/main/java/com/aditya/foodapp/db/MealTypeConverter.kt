package com.aditya.foodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters


@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun convertAnyToString(attribute:Any?):String{
        if(attribute==null){
            return ""
        }
        return attribute as String
    }
    @TypeConverter
    fun convertStringToAny(attribute:String?):Any{
        if(attribute==null){
            return ""
        }
        return attribute
    }
}