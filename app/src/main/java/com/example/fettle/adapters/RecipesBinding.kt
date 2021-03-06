package com.example.fettle.adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.fettle.NetworkStatus
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.modelClasses.Result
import com.example.fettle.roomdatabase.Entity
import com.example.fettle.ui.fragments.recipes.RecipesFragmentDirections

class RecipesBinding {

    companion object {
        //When CardView in recipes fragment is clicked, navigate to recipe details activity so that
        //info about that recipe can be displayed.
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipesRowLayout: ConstraintLayout, result: Result) {
            recipesRowLayout.setOnClickListener {
                try {
                    //Navigation action previously set up in nav.xml
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailsActivity(
                            result
                        )
                    recipesRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }

        }

        //Decides wether to show error image on recipes fragment.
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            response: NetworkStatus<FoodRecipe>?,
            database: List<Entity>?
        ) {
            //If there is a network error display the image, else keep the error image invisible
            if (response is NetworkStatus.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (response is NetworkStatus.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if (response is NetworkStatus.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        //Exact same as above but for the error text view. Only show it if there is a network error.
        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            response: NetworkStatus<FoodRecipe>?,
            database: List<Entity>?
        ) {
            if (response is NetworkStatus.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = response.message.toString()
            } else if (response is NetworkStatus.Loading) {
                textView.visibility = View.INVISIBLE
                textView.text = response.message.toString()
            } else if (response is NetworkStatus.Success) {
                textView.visibility = View.INVISIBLE
                textView.text = response.message.toString()
            }
        }
    }
}