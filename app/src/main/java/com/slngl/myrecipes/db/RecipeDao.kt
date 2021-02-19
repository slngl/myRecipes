package com.slngl.myrecipes.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe : Recipe)

    @Delete
    suspend fun deleteRecipe(recipe : Recipe)

    @Query("SELECT * FROM recipes")
    fun observeRecipes(): LiveData<List<Recipe>>
}