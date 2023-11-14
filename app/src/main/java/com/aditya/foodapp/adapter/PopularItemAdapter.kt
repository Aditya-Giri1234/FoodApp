package com.aditya.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.foodapp.databinding.SamplePopularItemBinding
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.bumptech.glide.Glide

class PopularItemAdapter : RecyclerView.Adapter<PopularItemAdapter.ViewHolder>() {
    private var mealList:ArrayList<PopularMeal> = ArrayList()

    lateinit var onItemClick:((PopularMeal)-> Unit)
    lateinit var onLongItemClick:((PopularMeal)->Unit)


    fun setMealList(list : ArrayList<PopularMeal>){
        this.mealList=list
        notifyDataSetChanged()
    }
    class ViewHolder( val view : SamplePopularItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(SamplePopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.view.ivPopularImage)
        holder.view.ivPopularImage.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
        holder.view.ivPopularImage.setOnLongClickListener(View.OnLongClickListener {
            onLongItemClick.invoke(mealList[position])
            true
        })

    }
}