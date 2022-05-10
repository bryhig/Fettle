package com.example.fettle.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fettle.CompareRecipes
import com.example.fettle.databinding.RecipesRowLayoutBinding
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.modelClasses.Result

//Adapts API data into recycler view in recipes fragment.
class AdaptAPI : RecyclerView.Adapter<AdaptAPI.MyViewHolder>() {
    private var recipes = emptyList<Result>()

    //Defines how to bind the recipes to the recycler view.
    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.response = result
            binding.executePendingBindings()
        }

        //Inflates the recipe row layout with the binding reacy for viewing.
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = recipes[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    //Compare old data to new using compare recipes class.
    fun setData(newData: FoodRecipe) {
        val compareRecipes = CompareRecipes(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(compareRecipes)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}