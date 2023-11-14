package com.aditya.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aditya.foodapp.activity.MainActivity
import com.aditya.foodapp.activity.MealActivity
import com.aditya.foodapp.adapter.MealItemAdapter
import com.aditya.foodapp.api.FavouriteCallback
import com.aditya.foodapp.databinding.FragmentFavouriteBinding
import com.aditya.foodapp.model.meal.Meal
import com.aditya.foodapp.utils.Constans
import com.aditya.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment(), FavouriteCallback {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var mealItemAdapter:MealItemAdapter
    private var deletePosition=-1
    private var deleteData:Meal?=null
    var isUndoClick=false
    var isSnackBarVisible=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(requireActivity() as MainActivity).viewModel
        mealItemAdapter= MealItemAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentFavouriteBinding.inflate(inflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToObserver()
    }

    private fun initUi() {
            binding.rvFavourite.apply {
                layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
                adapter=mealItemAdapter
            }

        mealItemAdapter.onItemClick={
            val intent= Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(Constans.MealIntent.MealId.name,it.idMeal)
            intent.putExtra(Constans.MealIntent.MealName.name,it.strMeal)
            intent.putExtra(Constans.MealIntent.MealThumb.name,it.strMealThumb)
            startActivity(intent)
        }
        val itemTouchHelper= object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                isUndoClick=false
                if(isSnackBarVisible){
                    lifecycleScope.launch {
                        deleteData?.let {
                            viewModel.delete(it)
                        }
                    }

                }
                 deletePosition=viewHolder.adapterPosition
                 deleteData=mealItemAdapter.differ.currentList[deletePosition]
                val mutableList = mealItemAdapter.differ.currentList.toMutableList()
                mutableList.removeAt(deletePosition)
                mealItemAdapter.differ.submitList(mutableList)
                if(mealItemAdapter.differ.currentList.isEmpty()){
                    noDataVisible()
                }


                Snackbar.make(requireView(),"Item delete successfully !",Snackbar.LENGTH_SHORT).setAction("Undo"){
                    val mutableList = mealItemAdapter.differ.currentList.toMutableList()
                    mutableList.add(deletePosition,deleteData)
                    mealItemAdapter.differ.submitList(mutableList)
                    noDataHide()
                    isUndoClick=true

                }.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        if(!isUndoClick){
                            lifecycleScope.launch {
                                deleteData?.let {
                                    viewModel.delete(it)
                                }
                            }
                        }
                        isSnackBarVisible=false
                    }

                    override fun onShown(sb: Snackbar?) {
                        super.onShown(sb)
                        isSnackBarVisible=true
                    }
                }).show()

            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourite)

    }
    private fun subscribeToObserver() {
        Log.e("NullCheck","viewModel->$viewModel")
        Log.e("NullCheck","observeFavouriteList->${viewModel.observeFavouriteList()}")
        Log.e("NullCheck","favouriteAdapter->$mealItemAdapter")
        viewModel.observeFavouriteList().observe(viewLifecycleOwner){
            Log.e("NullCheck","data->$it")
            if(!isSnackBarVisible){
                if(it.isEmpty()){
                    noDataVisible()
                }
                else{
                    noDataHide()
                    mealItemAdapter.sumbitList(it)
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
//        viewModel.getFavouriteMealList()
    }

    override fun onUndo() {
        val mutableList = mealItemAdapter.differ.currentList.toMutableList()
        mutableList.add(deletePosition,deleteData)
        mealItemAdapter.differ.submitList(mutableList)
        isUndoClick=true
    }

    override fun onDismiss(meal:Meal) {
        viewModel.delete(meal)
    }

    fun noDataVisible(){
        binding.rvFavourite.isGone=true
        binding.noResult.isGone=false
    }
    fun noDataHide(){
        binding.rvFavourite.isGone=false
        binding.noResult.isGone=true
    }

}