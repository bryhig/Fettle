package com.example.fettle.ui.fragments.recipes

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()
    //Set up binding.
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipeViewModel
    //Set up adapter.
    private val globalAdapter by lazy { AdaptAPI() }

    //Set up view models.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate layout.
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        //Call functions, see full functions below.
        setHasOptionsMenu(true)
        startRecycleView()
        readLocalData()

        //When filter floating action button is clicked, open the bottom sheet.
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_bottomSheet)
        }

        return binding.root
    }

    //Try and read data from the ROOM databse.
    private fun readLocalData() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                //If the database has data then display it in recycler view and stop shimmer effect.
                if (database.isNotEmpty() && !args.backFromBottom) {
                    globalAdapter.setData(database[0].recipe)
                    noShimmer()
                    //Else use retrofit to GET request spoonacular API.
                } else {
                    getData()
                }
            }
        }
    }

    //Function to get more data from spoonacular.
    private fun getData() {
        mainViewModel.get(recipeViewModel.getQueries())
        mainViewModel.APIresponse.observe(viewLifecycleOwner) { response ->
            //When network is okay stop shimmer effect and get data.
            when (response) {
                is NetworkStatus.Success -> {
                    noShimmer()
                    response.data?.let { globalAdapter.setData(it) }
                }
                //If theres a network error, show a message to user.
                is NetworkStatus.Error -> {
                    noShimmer()
                    getCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //If the network is loading show shimmer effect.
                is NetworkStatus.Loading -> {
                    shimmer()
                }
            }
        }
    }

    //Exact same as above but only used for the searchbar functionality.
    private fun search(query: String) {
        shimmer()
        mainViewModel.searchRecipe(recipeViewModel.applySearchQuery(query))
        mainViewModel.searchResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkStatus.Success -> {
                    noShimmer()
                    val foodRecipe = response.data
                    foodRecipe?.let { globalAdapter.setData(it) }
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

    //If the database is not empty then set the data on the screen to data from the ROOM database.
    private fun getCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    globalAdapter.setData(database[0].recipe)
                }
            }
        }
    }

    //Set up the recycle view.
    private fun startRecycleView() {
        binding.recyclerview.adapter = globalAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        shimmer()
    }

    //Set up the search bar functionality.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    //Show shimmer effect in recycler view using row_layout_placeholder.xml
    private fun shimmer() {
        binding.recyclerview.showShimmer()
    }

    //Stop the shimmer effect.
    private fun noShimmer() {
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //When a query is entered into the search bar, then search the query.
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            search(query)
        }
        return true
    }

    //Needed to make searchbar work.
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}

