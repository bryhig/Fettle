package com.example.fettle.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fettle.MainViewModel
import com.example.fettle.R
import com.example.fettle.adapters.adaptAPI
import com.example.fettle.Global.Companion.API_KEY
import com.example.fettle.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var mainViewModel : MainViewModel
    private val globalAdapter by lazy { adaptAPI() }
    private lateinit var globalView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        globalView = inflater.inflate(R.layout.fragment_recipes, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        startRecycleView()
        getData()

        return globalView
    }
    private fun getData() {
        mainViewModel.get(getQueries())
        mainViewModel.APIresponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkStatus.Success -> {
                    noShimmer()
                    response.data?.let { globalAdapter.setData(it) }
                }
                is NetworkStatus.Error -> {
                    noShimmer()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkStatus.Loading -> {
                    shimmer()
                }
            }
        }
    }
    private fun getQueries() : HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }
    private fun startRecycleView(){
        globalView.recycler_view.adapter = globalAdapter
        globalView.recycler_view.layoutManager = LinearLayoutManager(requireContext())
        shimmer()
    }
    private fun shimmer(){
        globalView.recycler_view.showShimmer()
    }
    private fun noShimmer(){
        globalView.recycler_view.hideShimmer()
    }
}
