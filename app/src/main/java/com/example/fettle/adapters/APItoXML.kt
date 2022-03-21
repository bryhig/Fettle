package com.example.fettle.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.fettle.R

class APItoXML {
    companion object{
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, url : String){
            imageView.load(url){
                crossfade(600)
            }

        }
        @BindingAdapter("vegan")
        @JvmStatic
        fun vegan(view : View, isVegan : Boolean){
            if(isVegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }

                }
            }

        }
    }
}