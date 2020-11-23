package com.roaa.ahmed_abu_elbukhari.ui.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.CategoryItem
import com.roaa.ahmed_abu_elbukhari.data.utils.Orientation
import com.roaa.ahmed_abu_elbukhari.ui.adapter.MealByCategoryAdapter
import com.roaa.ahmed_abu_elbukhari.databinding.FragmentCategoryBinding
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.android.synthetic.main.meals_by_category_item.*

class CategoryFragment : Fragment() {

    private lateinit var categoryBinding: FragmentCategoryBinding
    private val args: CategoryFragmentArgs by navArgs()
    private val categoryViewModel:
            MainViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         categoryBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        categoryBinding.lifecycleOwner = this

        exitTransition = MaterialElevationScale(/* growing= */ false)
        reenterTransition = MaterialElevationScale(/* growing= */ true)

        return categoryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryItem = CategoryItem(args.name,args.imageUrl,args.description)

        categoryBinding.apply {
            this.categoryItem = categoryItem
        }

        categoryViewModel.getMealsByCategory(args.name)

        val adapter = MealByCategoryAdapter(
            MealByCategoryAdapter
            .MealByCategoryListener{
                searchMealById(it)
            })


        categoryViewModel.mealByCategory.observe(viewLifecycleOwner){
            adapter.submitList(it)
            val layoutManger = Orientation(requireContext()).getLayoutMangerByOrientation()
                categoryBinding.apply {
                    this.recyclerView.adapter = adapter
                    this.recyclerView.layoutManager = layoutManger
                    this.progressBar.visibility = View.GONE
                }
        }

    }


    private fun searchMealById(mealName:String){
        val action = CategoryFragmentDirections.
        actionCategoryFragmentToDetailsFragment(mealName)
        val extras = FragmentNavigatorExtras(
            cardView_details to "shared_element_container_category" )
        findNavController().navigate(action,extras)
    }


}



