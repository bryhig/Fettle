package com.example.fettle.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fettle.RecipesDiffUtil
import com.example.fettle.databinding.RecipesRowLayoutBinding
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.modelClasses.Result

class AdaptAPI : RecyclerView.Adapter<AdaptAPI.MyViewHolder>() {
    private var recipes = emptyList<Result>()
    class MyViewHolder(private val binding : RecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.response = result
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
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

    fun setData(newData : FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}