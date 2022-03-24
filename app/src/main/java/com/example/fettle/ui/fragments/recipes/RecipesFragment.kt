package com.example.fettle.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fettle.viewmodels.MainViewModel
import com.example.fettle.R
import com.example.fettle.adapters.AdaptAPI
import com.example.fettle.NetworkStatus
import com.example.fettle.databinding.FragmentRecipesBinding
import com.example.fettle.observeOnce
import com.example.fettle.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding : FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel : MainViewModel
    private lateinit var recipeViewModel : RecipeViewModel
    private val globalAdapter by lazy { AdaptAPI() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        startRecycleView()
        readLocalData()

        return binding.root
    }

    private fun readLocalData() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    globalAdapter.setData(database[0].recipe)
                    noShimmer()
                }
                else{
                    getData()
                }
            }
        }
    }

    private fun getData() {
        mainViewModel.get(recipeViewModel.getQueries())
        mainViewModel.APIresponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkStatus.Success -> {
                    noShimmer()
                    response.data?.let { globalAdapter.setData(it) }
                }
                is NetworkStatus.Error -> {
                    noShimmer()
                    getCache()
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
    private fun getCache(){
       lifecycleScope.launch{
           mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
               if (database.isNotEmpty()) {
                   globalAdapter.setData(database[0].recipe)
               }
           }
       }
    }
    private fun startRecycleView(){
        binding.recyclerview.adapter = globalAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        shimmer()
    }
    private fun shimmer(){
        binding.recyclerview.showShimmer()
    }
    private fun noShimmer(){
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

