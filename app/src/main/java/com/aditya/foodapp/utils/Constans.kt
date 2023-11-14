package com.aditya.foodapp.utils

object Constans {

    // api call
    const val base_url="https://www.themealdb.com/api/json/v1/1/"
    const val randomPic="random.php"
    const val lookup="lookup.php"
    const val filter="filter.php"
    const val category="categories.php"
    const val search="search.php"
    const val tableName="mealInfo"
    const val dbName="MealDB"

    enum class MealIntent{
        MealId,
        MealName,
        MealThumb,
        CategoryName
    }

}