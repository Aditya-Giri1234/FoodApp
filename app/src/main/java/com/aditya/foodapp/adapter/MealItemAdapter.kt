package com.aditya.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.foodapp.databinding.SampleItemListBinding
import com.aditya.foodapp.model.meal.Meal
import com.bumptech.glide.Glide

class MealItemAdapter : RecyclerView.Adapter<MealItemAdapter.ViewHolder>() {

    val differ=AsyncListDiffer(this, diffUtil)
    lateinit var onItemClick:(Meal)->Unit
    companion object{
        val diffUtil=object :DiffUtil.ItemCallback<Meal>(){
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
               return oldItem.idMeal==newItem.idMeal
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem==newItem
            }

        }
    }
    class ViewHolder(val binding: SampleItemListBinding) : RecyclerView.ViewHolder(binding.root)

    fun sumbitList(list:List<Meal>?){
        differ.submitList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SampleItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=differ.currentList[position]
        Glide.with(holder.itemView).load(data.strMealThumb).into(holder.binding.ivCategoryView)
        holder.binding.tvCategoryText.text=data.strMeal
        holder.binding.cardCategory.setOnClickListener {
            if(::onItemClick.isInitialized)
            onItemClick.invoke(differ.currentList[position])
        }

    }
}