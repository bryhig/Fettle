package com.example.fettle.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.fettle.NetworkStatus
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.roomdatabase.Entity

class RecipesBinding {
    companion object{
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(imageView: ImageView, response : NetworkStatus<FoodRecipe>?, database : List<Entity>?){
            if(response is NetworkStatus.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }else if(response is NetworkStatus.Loading){
                imageView.visibility = View.INVISIBLE
            }else if(response is NetworkStatus.Success){
                imageView.visibility = View.INVISIBLE
            }
        }
        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(textView: TextView, response: NetworkStatus<FoodRecipe>?, database: List<Entity>?){
            if(response is NetworkStatus.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = response.message.toString()
            }else if(response is NetworkStatus.Loading){
                textView.visibility = View.INVISIBLE
                textView.text = response.message.toString()
            }else if(response is NetworkStatus.Success){
                textView.visibility = View.INVISIBLE
                textView.text = response.message.toString()
            }
        }
    }
}