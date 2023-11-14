package com.aditya.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aditya.foodapp.R
import com.aditya.foodapp.adapter.PopularItemAdapter
import com.aditya.foodapp.databinding.ActivityMainBinding
import com.aditya.foodapp.db.MealDatabase
import com.aditya.foodapp.utils.Helper
import com.aditya.foodapp.viewModel.HomeViewModel
import com.aditya.foodapp.viewModel.factory.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    public val viewModel by lazy {
        val mealDatabase=MealDatabase.getDatabase(this)
        val homeViewModelFactory=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this , homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Helper.setToast(applicationContext)

        NavigationUI.setupWithNavController(binding.bottomNav, Navigation.findNavController(this,
            R.id.navHost
        ))


    }


}