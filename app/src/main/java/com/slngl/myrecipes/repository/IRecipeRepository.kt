package com.slngl.myrecipes.repository

import androidx.lifecycle.LiveData
import com.slngl.myrecipes.db.Recipe
import com.slngl.myrecipes.network.model.DataHolder
import com.slngl.myrecipes.network.model.ImageResponse

interface IRecipeRepository {

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getRecipes(): LiveData<List<Recipe>>

    fun getRecipe(id : Int): LiveData<Recipe>

    fun updateRecipe(recipe: Recipe)

    suspend fun searchImage(imageString: String): DataHolder<ImageResponse>
}