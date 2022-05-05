package com.example.fettle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.fettle.R
import com.example.fettle.adapters.DetailsAdapter
import com.example.fettle.databinding.FragmentRecipeDetailsBindingImpl
import com.example.fettle.modelClasses.Result
import com.example.fettle.ui.fragments.RecipeDetails
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity() {
    private val args by navArgs<RecipeDetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val response = args.result
        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", response)
        val frag = RecipeDetails()
        frag.arguments = resultBundle
        supportFragmentManager.beginTransaction().add(R.id.recipeDetails, frag).commit()

    }

}