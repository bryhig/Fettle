package com.example.fettle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.navigation.navArgs
import com.example.fettle.R
import com.example.fettle.ui.fragments.recipes.RecipeDetails


class RecipeDetailsActivity : AppCompatActivity() {
    private val args by navArgs<RecipeDetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        //Prepare and send spoonacular data to the details fragment within this activity.
        val response = args.result
        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", response)
        //Send the data passed from recipes fragment to this activity, to the recipe details fragment.
        val frag = RecipeDetails()
        frag.arguments = resultBundle
        supportFragmentManager.beginTransaction().add(R.id.recipeDetails, frag).commit()

    }

}