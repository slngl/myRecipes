package com.slngl.myrecipes.repository

import androidx.lifecycle.LiveData
import com.slngl.myrecipes.db.Recipe
import com.slngl.myrecipes.db.RecipeDao
import com.slngl.myrecipes.network.PixabayAPI
import com.slngl.myrecipes.network.model.DataHolder
import com.slngl.myrecipes.network.model.ImageResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val pixabayAPI: PixabayAPI
) : IRecipeRepository {
    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override fun getRecipes(): LiveData<List<Recipe>> {
        return recipeDao.observeRecipes()
    }

    override fun getRecipe(id: Int): LiveData<Recipe> {
        return recipeDao.getRecipe(id)
    }

    override fun updateRecipe(recipe: Recipe) {
        return recipeDao.updateRecipe(recipe)
    }

    override suspend fun searchImage(imageString: String): DataHolder<ImageResponse> {
        return try {
            val response = pixabayAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let DataHolder.success(it)
                } ?: DataHolder.error("Error from pixabay", null)
            } else {
                DataHolder.error("No Data!", null)
            }
        }catch (e: Exception){
            DataHolder.error("No Data!", null)

        }
    }
}