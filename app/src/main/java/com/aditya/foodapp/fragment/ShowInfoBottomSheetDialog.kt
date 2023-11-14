package com.aditya.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.foodapp.R
import com.aditya.foodapp.activity.MainActivity
import com.aditya.foodapp.activity.MealActivity
import com.aditya.foodapp.databinding.FragmentCategoryBinding
import com.aditya.foodapp.databinding.InfoBottomSheetBinding
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowInfoBottomSheetDialog(val id:String): BottomSheetDialogFragment() {

    lateinit var binding:InfoBottomSheetBinding
    lateinit var viewModel: HomeViewModel
    lateinit var idMeal:String
    lateinit var strMeal:String
    lateinit var mealThumb:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(requireActivity() as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=InfoBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
         subscribeToObserver()
    }

    private fun subscribeToObserver() {
       viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner){
           Glide.with(this).load(it.strMealThumb).into(binding.bottomIvMeal)
           binding.bottomTvLocation.text=it.strArea
           binding.bottomTvLocation.text=it.strCategory
           binding.bottomTvMealName.text=it.strMeal

           idMeal=it.idMeal
           strMeal= it.strMeal.toString()
           mealThumb= it.strMealThumb.toString()


       }
    }

    private fun initUi() {
   viewModel.getMealById(id)

        binding.bottomSheetInfo.setOnClickListener {
            val intent= Intent(requireContext(),MealActivity::class.java)
            intent.putExtra(Constans.MealIntent.MealId.name,idMeal)
            intent.putExtra(Constans.MealIntent.MealName.name,strMeal)
            intent.putExtra(Constans.MealIntent.MealThumb.name,mealThumb)
            requireContext().startActivity(intent)
            dismiss()
        }
    }



}