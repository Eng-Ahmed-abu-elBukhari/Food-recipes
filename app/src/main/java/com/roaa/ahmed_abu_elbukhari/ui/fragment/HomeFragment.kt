package com.roaa.ahmed_abu_elbukhari.ui.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.ui.adapter.LastMealAdapter
import com.roaa.ahmed_abu_elbukhari.ui.adapter.MealCategoryAdapter
import com.roaa.ahmed_abu_elbukhari.ui.interfaces.OnClickItemInterface
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.MealCategoryObject
import com.roaa.ahmed_abu_elbukhari.data.utils.Orientation
import com.roaa.ahmed_abu_elbukhari.databinding.FragmentHomeBinding
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.android.synthetic.main.last_meals_item.*
import kotlinx.android.synthetic.main.meals_category_item.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.FieldPosition


class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private val homeViewModel: MainViewModel by navGraphViewModels(R.id.nav_graph)
    private var onclickInterface: OnClickItemInterface? = null
    private lateinit var categories: List<MealCategoryObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        reenterTransition = MaterialElevationScale(/* growing= */ true)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        fragmentHomeBinding.lifecycleOwner = this
        homeViewModel.initializationMealInfo()
        return fragmentHomeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onclickInterface = object : OnClickItemInterface {
            override fun setOnClick(pos: Int) {
                val action = HomeFragmentDirections.
                actionHomeFragmentToCategoryFragment(
                    categories[pos].categoryName,
                    categories[pos].strCategoryDescription,
                    categories[pos].categoryImage
                )
                val extras = FragmentNavigatorExtras(
                    home_cardView_Category to "shared_element_container")
                findNavController().navigate(action, extras)
            }
        }
        val adapterCategory = MealCategoryAdapter(onclickInterface as OnClickItemInterface)

        val adapterMeal = LastMealAdapter(LastMealAdapter.LastMealListener {
            startSearchMealByName(it)
        })

        GlobalScope.launch {
            async(Dispatchers.Main) {
                homeViewModel.mealCategory.observe(viewLifecycleOwner, { it ->
                    adapterCategory.submitList(it)
                    fragmentHomeBinding.shimmerCategoryContainer.stopShimmer()
                    fragmentHomeBinding.shimmerCategoryContainer.visibility = View.GONE
                    categories = it
                    val layoutManger = Orientation(requireContext()).getLayoutMangerByOrientation()
                        fragmentHomeBinding.apply {
                            this.recyclerViewCategory.layoutManager = layoutManger
                            this.recyclerViewCategory.adapter = adapterCategory
                        }
                })
            }.await()

            async(Dispatchers.Main) {
                homeViewModel.meals.observe(viewLifecycleOwner, {
                    adapterMeal.submitList(it)
                    fragmentHomeBinding.shimmerMealContainer.stopShimmer()
                    fragmentHomeBinding.shimmerMealContainer.visibility = View.GONE

                    fragmentHomeBinding.apply {
                        val layoutManger = LinearLayoutManager(
                            context,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        this.recyclerViewMeal.layoutManager = layoutManger
                        this.recyclerViewMeal.adapter = adapterMeal
                    }

                })
            }.await()
        }

    }


    private fun startSearchMealByName(mealName: String) {
        val action = HomeFragmentDirections.
        actionHomeFragmentToDetailsFragment(mealName = mealName)

        val extras = FragmentNavigatorExtras(
            card_last_meal to "shared_element_container_category")

        findNavController().navigate(action,extras)
    }


    override fun onResume() {
        super.onResume()
        shimmerStart()
    }

    override fun onPause() {
        super.onPause()
        shimmerStop()
    }



    private fun shimmerStop(){
        fragmentHomeBinding.shimmerCategoryContainer.stopShimmer()
        fragmentHomeBinding.shimmerMealContainer.stopShimmer()
    }

    private fun shimmerStart(){
        fragmentHomeBinding.shimmerCategoryContainer.startShimmer()
        fragmentHomeBinding.shimmerMealContainer.startShimmer()
    }


}