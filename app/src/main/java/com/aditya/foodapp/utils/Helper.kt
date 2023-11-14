package com.aditya.foodapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Helper {
    private var toast:Toast?=null

    fun setToast(context: Context){
        toast=Toast(context)
    }


    fun showToast(message:String){
        toast?.apply {
            cancel()
            setText(message)
            duration=Toast.LENGTH_SHORT
            show()
        }
    }
}