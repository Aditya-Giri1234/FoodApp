package com.aditya.foodapp.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.aditya.foodapp.R
import com.aditya.foodapp.adapter.PopularItemAdapter
import com.aditya.foodapp.databinding.ActivityMealBinding
import com.aditya.foodapp.db.MealDatabase
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.utils.Helper
import com.aditya.foodapp.viewModel.MealViewModel
import com.aditya.foodapp.viewModel.factory.MealViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var youTubeLink:String
    private var mealToSave:Meal?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase=MealDatabase.getDatabase(this)
        val mealViewModelFactory=MealViewModelFactory(mealDatabase)
        mealViewModel=ViewModelProvider(this,mealViewModelFactory)[MealViewModel::class.java]


        loadingCase()
        initUi()
        subscribeObserver()

    }

    private fun subscribeObserver() {
        mealViewModel.observeMealData().observe(this){
            onResponseCase()
            mealToSave=it
            binding.tvCategory.text=it.strCategory
            binding.tvArea.text=it.strArea
            binding.tvFoodData.text=it.strInstructions
            this.youTubeLink= it.strYoutube.toString()

        }

    }


    private fun initUi() {
        Glide.with(this@MealActivity).load(intent.getStringExtra(Constans.MealIntent.MealThumb.name)).into(binding.ivMealPic)
        binding.collapsingToolbar.title=intent.getStringExtra(Constans.MealIntent.MealName.name)
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE)
        intent.getStringExtra(Constans.MealIntent.MealId.name)
            ?.let { mealViewModel.getPicDetailsById(it) }

        binding.ivVideo.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(this.youTubeLink))
            startActivity(intent)
        }
        binding.btnFloatingFavourite.setOnClickListener{
            mealToSave?.let {
                mealViewModel.upsert(it)
                Helper.showToast("Meal Saved Successfully !")
            }
        }
    }

    private fun loadingCase(){
        binding.progressHorizontal.isGone=false
        binding.btnFloatingFavourite.isGone=true
        binding.tvCategory.isGone=true
        binding.tvArea.isGone=true
        binding.tvInstruction.isGone=true
        binding.tvFoodData.isGone=true
        binding.mealInfoLoader.isGone=false
        binding.ivMealPic.isGone=true
    }

    private fun onResponseCase(){
        binding.progressHorizontal.isGone=true
        binding.btnFloatingFavourite.isGone=false
        binding.tvCategory.isGone=false
        binding.tvArea.isGone=false
        binding.tvInstruction.isGone=false
        binding.tvFoodData.isGone=false
        binding.mealInfoLoader.isGone=true
        binding.ivMealPic.isGone=false
    }
}