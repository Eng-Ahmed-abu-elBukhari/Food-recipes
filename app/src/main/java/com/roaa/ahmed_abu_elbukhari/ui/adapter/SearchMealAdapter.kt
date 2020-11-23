package com.roaa.ahmed_abu_elbukhari.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.MealObject
import com.roaa.ahmed_abu_elbukhari.databinding.SearchMealItemBinding

class SearchMealAdapter (private val clickListener :SearchMealListener):
    ListAdapter<MealObject, SearchMealAdapter.ViewHolder>(LastMealDiffCallBack()) {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMeal = getItem(position)
        holder.bind(itemMeal!!,clickListener)
    }


    class ViewHolder(private val binding: SearchMealItemBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun bind(results: MealObject, clickListener: SearchMealListener){
            binding.lastMeal = results
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchMealItemBinding.inflate(
                    layoutInflater,parent,false)
                return ViewHolder(binding) }
        }
    }



    class LastMealDiffCallBack : DiffUtil.ItemCallback<MealObject>(){
        override fun areItemsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
            return oldItem.idMeal == newItem.idMeal }
        override fun areContentsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
            return oldItem == newItem }
    }


    class SearchMealListener(val clickListener : (categoryName : String) -> Unit){
        fun onClick(lastMeal: MealObject) {
            clickListener(lastMeal.mealName)
        }
    }



}
