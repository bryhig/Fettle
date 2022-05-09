package com.example.fettle.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load


class APItoXML {
    companion object {
        //Use binding adapter in recipes fragment XML, to load the image obatined from spoonacular
        //response using coil image loading library.
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }

        }

    }
}