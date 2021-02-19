package com.slngl.myrecipes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.slngl.myrecipes.R
import com.slngl.myrecipes.db.Recipe
import javax.inject.Inject

class RecipeRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder>() {


    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.recipeRowImageView)
        val nameText =  itemView.findViewById<TextView>(R.id.recipeRowName)
        val yieldText =  itemView.findViewById<TextView>(R.id.recipeRowYield)
        val timeText = itemView.findViewById<TextView>(R.id.recipeRowTime)
        fun setData(recipe: Recipe){
            itemView.apply {
                nameText.text = "Name: ${recipe.name}"
                yieldText.text = recipe.yield
                timeText.text = recipe.time
                glide.load(recipe.imageURL).into(imageView)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var recipes: List<Recipe>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_row, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes.get(position)
        holder.setData(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}