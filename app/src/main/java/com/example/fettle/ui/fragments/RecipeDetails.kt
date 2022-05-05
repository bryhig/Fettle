package com.example.fettle.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import coil.load
import com.example.fettle.R
import com.example.fettle.modelClasses.ExtendedIngredient
import com.example.fettle.modelClasses.Result
import com.example.fettle.ui.RecipeDetailsActivity
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*

class RecipeDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        val args = arguments
        val myBundle : Result? = args?.getParcelable("recipeBundle")
        view.imageViewDetails.load(myBundle?.image)
        view.textViewTitleDetails.text = myBundle?.title
        val mins = "Ready in " + myBundle?.readyInMinutes.toString() + " mins"
        view.textViewMinsDetails.text = mins
        view.textViewOverview.text = myBundle?.summary


        return view
    }
}