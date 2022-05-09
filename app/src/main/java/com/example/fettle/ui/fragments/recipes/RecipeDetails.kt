package com.example.fettle.ui.fragments.recipes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import coil.load
import com.example.fettle.R
import com.example.fettle.modelClasses.Result
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import org.jsoup.Jsoup

class RecipeDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        //Set up fragment arguements. The selected recipes data is passed from recipes fragment to this fragment.
        val args = arguments
        //Get the parcelized recipe data in a bundle from recipes fragment.
        val myBundle: Result? = args?.getParcelable("recipeBundle")
        //Set up recipe details to display in this fragment.
        val mins = "Ready in " + myBundle?.readyInMinutes.toString() + " mins"
        view.imageViewDetails.load(myBundle?.image)
        view.textViewTitleDetails.text = myBundle?.title
        view.textViewReadyInMins.text = mins
        //Get rid of HTML tags which appear from some spoonacular recipes.
        view.textViewOverview.text = html2text(myBundle?.summary.toString())
        view.textViewURL.text = myBundle?.sourceUrl.toString()
        view.button.setOnClickListener {
            copyToClipboard(myBundle?.title, myBundle?.sourceUrl.toString())
        }
        return view
    }

    //Recipe summary contains HTML sometimes, so this function uses Jsoup to parse it to text.
    private fun html2text(input: String): String {
        return Jsoup.parse(input).text()
    }

    //Copies the recipes details to the devices clipboard to be later pasted into the meal planner.
    private fun copyToClipboard(title: String?, url: String?) {
        //Set up text to be added to clipboard.
        val text = title + " - " + url
        //Copy text to clipboard.
        val clipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
        //Show message to alert user.
        Toast.makeText(context, "Recipe copied to clipboard", Toast.LENGTH_LONG).show()
    }

}