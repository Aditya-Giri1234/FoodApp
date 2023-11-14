package com.aditya.foodapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aditya.foodapp.adapter.CategoryAdapter
import com.aditya.foodapp.databinding.ActivityMealCategoryBinding
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.viewModel.CategoryViewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealCategoryBinding
    private lateinit var categoryMealViewModel:CategoryViewModel

    private val categoryAdapter : CategoryAdapter by lazy {
        CategoryAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealViewModel=ViewModelProvider(this)[CategoryViewModel::class.java]
        initUI()
        subscribeToObserver()
    }

    private fun subscribeToObserver() {
        categoryMealViewModel.observeCategoryMealData().observe(this){
            categoryAdapter.setCategory(it.meals as ArrayList<PopularMeal>)
            binding.tvItemSize.text="Items : ${it.meals.size}"
        }
    }

    private fun initUI() {
        intent.getStringExtra(Constans.MealIntent.CategoryName.name)
            ?.let { categoryMealViewModel.getMealByCategory(it) }

        binding.rvCategories.let {
            it.layoutManager=GridLayoutManager(this@CategoryActivity,2,GridLayoutManager.VERTICAL,false)
            it.adapter=categoryAdapter
        }
        categoryAdapter.onItemClick={
            val intent= Intent(this@CategoryActivity,MealActivity::class.java)
            intent.putExtra(Constans.MealIntent.MealId.name,it.idMeal)
            intent.putExtra(Constans.MealIntent.MealName.name,it.strMeal)
            intent.putExtra(Constans.MealIntent.MealThumb.name,it.strMealThumb)
            startActivity(intent)
        }
    }
}