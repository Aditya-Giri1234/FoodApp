package com.aditya.foodapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.foodapp.databinding.SampleCategoryBinding
import com.aditya.foodapp.databinding.SampleItemListBinding
import com.aditya.foodapp.model.category.Category
import com.aditya.foodapp.model.popularMeal.PopularMeal
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var categoryList=ArrayList<PopularMeal>()
    lateinit var onItemClick:(PopularMeal)->Unit

    fun setCategory(list:ArrayList<PopularMeal>){
        this.categoryList=list
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: SampleItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SampleItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(categoryList[position].strMealThumb).apply(RequestOptions().override(160, 160)).into(holder.binding.ivCategoryView)
        holder.binding.tvCategoryText.text=categoryList[position].strMeal
        categoryList[position].strMealThumb?.let { resizeImage(holder.binding.ivCategoryView, it) }

        holder.binding.cardCategory.setOnClickListener {
            onItemClick.invoke(categoryList[position])
        }

    }

    private fun resizeImage(view : ImageView,uri:String) {
//        val imageUri: Uri =Uri.parse(uri)
//        val source = ImageDecoder.createSource(, imageUri)
//        mYourBitmap = ImageDecoder.decodeBitmap(source)
//        mImageView.setImageBitmap(mYourBitmap)
    }
}