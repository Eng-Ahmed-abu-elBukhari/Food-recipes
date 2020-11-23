package com.roaa.ahmed_abu_elbukhari.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.data.utils.Orientation
import com.roaa.ahmed_abu_elbukhari.ui.adapter.SearchMealAdapter
import com.roaa.ahmed_abu_elbukhari.databinding.FragmentSearchBinding
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.search_meal_item.*


class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: MainViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.lifecycleOwner = this
        setHasOptionsMenu(true)

        return searchBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBinding.searchToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        searchBinding.inputSearchMeal.setOnEditorActionListener(OnEditorActionListener {
                searchName, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.getMealByLetter(searchName.text.toString())
                return@OnEditorActionListener true
            }
            false
        })



        val adapter = SearchMealAdapter(SearchMealAdapter.SearchMealListener{
            searchMealByName(it)

        })

        searchViewModel.mealDetailsByLetter.observe(viewLifecycleOwner){
            adapter.submitList(it)
                val layoutManger = Orientation(requireContext()).getLayoutMangerByOrientation()
            searchBinding.apply {
                    this.recyclerViewSearch.adapter = adapter
                    this.recyclerViewSearch.layoutManager = layoutManger
                }
            }
        }


    private fun searchMealByName(mealName:String){
        val action = SearchFragmentDirections
            .actionSearchFragmentToDetailsFragment(mealName)
        val extra = FragmentNavigatorExtras(
            search_cardView to "shared_element_container_category"
        )
        findNavController().navigate(action,extra)
    }



}
