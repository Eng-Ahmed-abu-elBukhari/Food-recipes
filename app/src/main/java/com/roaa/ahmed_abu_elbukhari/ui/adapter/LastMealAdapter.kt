package com.roaa.ahmed_abu_elbukhari.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.MealObject
import com.roaa.ahmed_abu_elbukhari.databinding.LastMealsItemBinding

class LastMealAdapter (private val clickListener :LastMealListener):
    ListAdapter<MealObject,LastMealAdapter.ViewHolder>(LastMealDiffCallBack()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMeal = getItem(position)
        holder.bind(itemMeal!!,clickListener)
    }


    class ViewHolder(private val binding: LastMealsItemBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun bind(results: MealObject, clickListener: LastMealListener){
            binding.lastMealObject = results
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent:ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LastMealsItemBinding.inflate(
                    layoutInflater,parent,false)
                return ViewHolder(binding) }
        }
    }



    class LastMealDiffCallBack : DiffUtil.ItemCallback<MealObject>(){
        override fun areItemsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
            return oldItem == newItem }
    }


    class LastMealListener(val clickListener : (categoryName : String) -> Unit){
        fun onClick(lastMeal: MealObject) {
            clickListener(lastMeal.mealName)
        }
        }



}
