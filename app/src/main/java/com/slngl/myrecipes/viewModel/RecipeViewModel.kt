package com.slngl.myrecipes.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slngl.myrecipes.db.Recipe
import com.slngl.myrecipes.network.model.DataHolder
import com.slngl.myrecipes.network.model.ImageResponse
import com.slngl.myrecipes.repository.IRecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel @ViewModelInject constructor(private val repository: IRecipeRepository) :
    ViewModel() {

    //RecipeFragment
    val recipeList = repository.getRecipes()

    lateinit var recipe: LiveData<Recipe>

    fun getRecipe(id: Int) = viewModelScope.launch {
        recipe = repository.getRecipe(id)
    }

    //ImageAPIFragment

    private val images = MutableLiveData<DataHolder<ImageResponse>>()
    val imageList: LiveData<DataHolder<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

//    RecipeDetailFragment

    private var insertRecipeMsg = MutableLiveData<DataHolder<Recipe>>()
    val insertMessage: LiveData<DataHolder<Recipe>>
        get() = insertRecipeMsg

    fun resetInsertRecipeMsg() {
        insertRecipeMsg = MutableLiveData<DataHolder<Recipe>>()
    }

    fun updateRecipe(recipe: Recipe){
        repository.updateRecipe(recipe)
    }

    fun setSelectedImage(url: String) = selectedImage.postValue(url)


    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

    fun insertRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun makeRecipe(
        name: String, mYield: String, totalTime: String,
        method: String, ingredients: String,
    ) {
        if (name.isEmpty() || mYield.isEmpty() || totalTime.isEmpty() || method.isEmpty() || ingredients.isEmpty()) {
            insertRecipeMsg.postValue(DataHolder.error("Enter name, yield, total time, ingreidents and method", null))
            return
        }
        val recipe = Recipe(name, mYield, totalTime, method, ingredients, selectedImage.value ?: "")
        insertRecipe(recipe)
        setSelectedImage("")
        insertRecipeMsg.postValue(DataHolder.success(recipe))
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = DataHolder.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

}