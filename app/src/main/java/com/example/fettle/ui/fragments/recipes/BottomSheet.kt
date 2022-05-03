package com.example.fettle.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.fettle.Global.Companion.DEFAULT_COURSE
import com.example.fettle.Global.Companion.DEFAULT_DIET
import com.example.fettle.R
import com.example.fettle.viewmodels.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*
import kotlin.Exception

class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipeViewModel: RecipeViewModel

    private var mealChip = DEFAULT_COURSE
    private var mealChipID = 0
    private var dietChip = DEFAULT_DIET
    private var dietChipID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        recipeViewModel.readTypes.asLiveData().observe(viewLifecycleOwner) { value ->
            mealChip = value.selectedDishType
            dietChip = value.selectedDietType
            updateChip(value.selectedDietID, mView.diet_ChipGroup)
            updateChip(value.selectedDishID, mView.type_ChipGroup)
        }
        mView.type_ChipGroup.setOnCheckedChangeListener { group, id ->
            val chip = group.findViewById<Chip>(id)
            val type = chip.text.toString().lowercase()
            mealChip = type
            mealChipID = id
        }
        mView.diet_ChipGroup.setOnCheckedChangeListener { group, id ->
            val chip = group.findViewById<Chip>(id)
            val type = chip.text.toString().lowercase()
            dietChip = type
            dietChipID = id
        }
        mView.filter_button.setOnClickListener {
            recipeViewModel.saveTypes(mealChip, mealChipID, dietChip, dietChipID)
            val action = BottomSheetDirections.actionBottomSheetToRecipesFragment(backFromBottom = true)
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(id: Int, chipGroup: ChipGroup) {
        if (id != 0) {
            try {
                chipGroup.findViewById<Chip>(id).isChecked = true
            } catch (e: Exception) {
                Log.d("BottomSheet", e.message.toString())
            }
        }
    }
}
