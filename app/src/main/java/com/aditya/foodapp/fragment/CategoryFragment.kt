package com.aditya.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aditya.foodapp.activity.CategoryActivity
import com.aditya.foodapp.activity.MainActivity
import com.aditya.foodapp.adapter.CategoryMealAdapter
import com.aditya.foodapp.databinding.FragmentCategoryBinding
import com.aditya.foodapp.model.category.Category
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.viewModel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var viewModel: HomeViewModel
    lateinit var categoryAdapter: CategoryMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(requireActivity() as MainActivity).viewModel
        categoryAdapter= CategoryMealAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToObserver()
    }

    private fun subscribeToObserver() {
       viewModel.observeCategoryData().observe(viewLifecycleOwner){
           categoryAdapter.setCategory(it.categories as ArrayList<Category>)
       }
    }

    private fun initUi() {
       binding.rvCategories.apply {
           layoutManager=GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
           adapter=categoryAdapter
       }
        categoryAdapter.onItemClick={
           val intent= Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra(Constans.MealIntent.CategoryName.name,it.strCategory)
            startActivity(intent)
        }
    }

}