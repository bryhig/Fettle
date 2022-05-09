package com.example.fettle.modelClasses


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
//The result class defines the attributes of a spoonacular recipe.
//There are many more properties available, I've only kept the ones i use in the app here.
data class Result(
    @SerializedName("dairyFree")
    val dairyFree: Boolean?,
    @SerializedName("glutenFree")
    val glutenFree: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vegan")
    val vegan: Boolean?,
    @SerializedName("vegetarian")
    val vegetarian: Boolean?,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean?
): Parcelable