package com.example.fettle.ui.fragments.mealplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.fettle.Global.Companion.MEAL_PLANNER_URL
import com.example.fettle.NetworkStatus
import com.example.fettle.R
import kotlinx.android.synthetic.main.fragment_meal_planner.*
import kotlinx.android.synthetic.main.fragment_meal_planner.view.*

class MealPlanner : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_planner, container, false)
        //Meal planner makes use of Google Calendars. It loads Google Calendars in a WebView for the
        //user to interact with.
        //in-app. Allows the user to see the meal plan on an array of devices.
        //Create WebViewClient.
        view.calenderWebView.webViewClient = object : WebViewClient() {}
        //Enable JavaScript, otherwise it doesnt work.
        view.calenderWebView.settings.javaScriptEnabled = true
        view.calenderWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        view.calenderWebView.settings.useWideViewPort = true
        view.calenderWebView.isScrollbarFadingEnabled = false
        //Load google calendar URL and display in fragment.
        view.calenderWebView.loadUrl(MEAL_PLANNER_URL)
        showToast()
        return view;
    }

    //Show prompt telling user to login with google account to use meal planner.
    fun showToast() {
        Toast.makeText(
            requireContext(),
            "Please log in with your Google account to use meal planner.",
            Toast.LENGTH_LONG
        ).show()
    }
}