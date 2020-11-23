package com.roaa.ahmed_abu_elbukhari.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roaa.ahmed_abu_elbukhari.data.model.meal_category.MealCategoryObject
import com.roaa.ahmed_abu_elbukhari.databinding.MealsCategoryItemBinding
import com.roaa.ahmed_abu_elbukhari.ui.interfaces.OnClickItemInterface

class MealCategoryAdapter(private val onClickInterface: OnClickItemInterface):
    ListAdapter<MealCategoryObject,MealCategoryAdapter.ViewHolder>(MealCategoryDiffCallback()
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCategory = getItem(position)
        holder.bind(itemCategory!!,onClickInterface,position)
    }






    class ViewHolder(private val binding: MealsCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(results: MealCategoryObject, onClickInterface: OnClickItemInterface, position: Int){
            binding.mealCategory = results
            binding.homeCardViewCategory.setOnClickListener {
                onClickInterface.setOnClick(position)
            }
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealsCategoryItemBinding.inflate(
                    layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }






    class MealCategoryDiffCallback :
        DiffUtil.ItemCallback<MealCategoryObject>() {
        override fun areItemsTheSame(oldItem: MealCategoryObject, newItem: MealCategoryObject): Boolean {
            return oldItem.idCategory == newItem.idCategory }
        override fun areContentsTheSame(oldItem: MealCategoryObject, newItem: MealCategoryObject): Boolean {
            return oldItem == newItem
        }
    }


    /**
    class MealCategoryListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(mealsCategory: CategoryObject) = clickListener(mealsCategory.idCategory.toInt())
    }

*/

}





