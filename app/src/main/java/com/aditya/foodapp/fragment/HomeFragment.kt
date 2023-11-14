package com.aditya.foodapp.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.foodapp.R
import com.aditya.foodapp.activity.MealActivity
import com.aditya.foodapp.activity.CategoryActivity
import com.aditya.foodapp.activity.MainActivity
import com.aditya.foodapp.adapter.CategoryMealAdapter
import com.aditya.foodapp.adapter.PopularItemAdapter
import com.aditya.foodapp.databinding.FragmentHomeBinding
import com.aditya.foodapp.model.category.Category
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.viewModel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val TAG="HomeFragment"
    private lateinit var intent:Intent
    private lateinit var randomMeal: PopularMeal
    lateinit var popularMeal:PopularMeal
    private lateinit var viewModel: HomeViewModel
    private val popularItemAdapter: PopularItemAdapter by lazy {
        PopularItemAdapter()
    }
    private val categoryMealAdapter : CategoryMealAdapter by lazy {
        CategoryMealAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(requireActivity() as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUI()
        viewModel.getRandomMeal()
        viewModel.getPopularMeal("Seafood")
        viewModel.getCategory()

        subscribeToObserver()




    }

    private fun subscribeToObserver() {
        viewModel.observeRandomMealData().observe(viewLifecycleOwner){
            binding.randomLoader.isGone=true
            Glide.with(this@HomeFragment).load(it.strMealThumb).into(binding.ivCardWelcomeHome)
            this.randomMeal=PopularMeal(it.idMeal,it.strMeal,it.strMealThumb)
        }
        viewModel.observePopularMealData().observe(viewLifecycleOwner){
            popularItemAdapter.setMealList(it.meals as ArrayList<PopularMeal>)
        }
        viewModel.observeCategoryData().observe(viewLifecycleOwner) {
            categoryMealAdapter.setCategory(it.categories as ArrayList<Category>)
        }

    }

    private fun initUI() {
        binding.rvPopularItem.let {
            it.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
          it.adapter=popularItemAdapter
        }
        binding.rvHomeCategories.let {
            it.layoutManager=GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
            it.adapter=categoryMealAdapter
        }
        binding.ivHomeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.cardWelcomeHome.setOnClickListener {
            intent=Intent(activity,MealActivity::class.java)
            setIntent(0)
            activity?.startActivity(intent)
        }
        popularItemAdapter.onItemClick={
             intent=Intent(requireContext(),MealActivity::class.java)
            this.popularMeal=it
            setIntent(1)
            startActivity(intent)
        }

        categoryMealAdapter.onItemClick={
            intent=Intent(requireContext(),CategoryActivity::class.java)
            intent.putExtra(Constans.MealIntent.CategoryName.name,it.strCategory)
            startActivity(intent)
        }
        popularItemAdapter.onLongItemClick={
            val dialog=ShowInfoBottomSheetDialog(it.idMeal)
            dialog.show(childFragmentManager,"Meal Info")
        }
    }

    private fun setIntent(intentType:Int) {
        val meal = when(intentType){
            0->{
                randomMeal
            }

            1->{
                popularMeal
            }

            else->{
                randomMeal
            }
        }
        intent.putExtra(Constans.MealIntent.MealId.name,meal.idMeal)
        intent.putExtra(Constans.MealIntent.MealName.name,meal.strMeal)
        intent.putExtra(Constans.MealIntent.MealThumb.name,meal.strMealThumb)
    }


}