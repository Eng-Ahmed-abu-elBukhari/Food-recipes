package com.roaa.ahmed_abu_elbukhari.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roaa.ahmed_abu_elbukhari.data.model.meal_by_category.MealObject
import com.roaa.ahmed_abu_elbukhari.databinding.MealsByCategoryItemBinding


class MealByCategoryAdapter(private val clickListener: MealByCategoryListener):
    ListAdapter<MealObject, MealByCategoryAdapter.ViewHolder>(
        MealByCategoryDiffCallBack()
    ){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMealByCategory = getItem(position)
        holder.bind(itemMealByCategory!!, clickListener)
    }


    class ViewHolder(val binding: MealsByCategoryItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(results: MealObject, listener: MealByCategoryListener){
            binding.mealObject = results
            binding.clickListener = listener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealsByCategoryItemBinding.inflate(
                    layoutInflater, parent, false
                )

                return ViewHolder(binding)
            }
        }
    }

    class MealByCategoryDiffCallBack:DiffUtil.ItemCallback<MealObject>(){
        override fun areItemsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
          return oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: MealObject, newItem: MealObject): Boolean {
            return oldItem == newItem
        }
    }

    class MealByCategoryListener(val clickListener: (meal_name: String) -> Unit){
        fun onClick(item: MealObject){

            clickListener(item.mealName)
        }
    }






}