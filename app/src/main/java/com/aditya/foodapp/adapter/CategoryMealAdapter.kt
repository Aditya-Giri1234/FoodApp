package com.aditya.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.foodapp.databinding.SampleCategoryBinding
import com.aditya.foodapp.model.category.Category
import com.bumptech.glide.Glide

class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.ViewHolder>() {
    private var categoryList=ArrayList<Category>()
    lateinit var onItemClick:((Category)-> Unit)

    fun setCategory(list:ArrayList<Category>){
        this.categoryList=list
        notifyDataSetChanged()
    }
    class ViewHolder(val binding:SampleCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(SampleCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.ivCategory)
        holder.binding.tvCategory.text=categoryList[position].strCategory
        holder.binding.consCategory.setOnClickListener {
            onItemClick.invoke(categoryList[position])
        }
    }
}