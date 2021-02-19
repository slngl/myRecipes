package com.slngl.myrecipes.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.slngl.myrecipes.adapter.ImageRecyclerAdapter
import com.slngl.myrecipes.adapter.RecipeRecyclerAdapter
import javax.inject.Inject

class RecipeFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val recipeRecyclerAdapter: RecipeRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            RecipeDetailsFragment::class.java.name -> RecipeDetailsFragment(glide)
            RecipeFragment::class.java.name -> RecipeFragment(recipeRecyclerAdapter)
            ImageFromAPIFragment::class.java.name -> ImageFromAPIFragment(imageRecyclerAdapter)
            else ->  super.instantiate(classLoader, className)

        }

    }
}