package com.slngl.myrecipes.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slngl.myrecipes.R
import com.slngl.myrecipes.adapter.RecipeRecyclerAdapter
import com.slngl.myrecipes.databinding.FragmentRecipesBinding
import com.slngl.myrecipes.viewModel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment @Inject constructor(private val recipeRecyclerAdapter: RecipeRecyclerAdapter) :
    Fragment(R.layout.fragment_recipes) {

    private var fragmentBinding: FragmentRecipesBinding? = null

    lateinit var viewModel: RecipeViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT /*or ItemTouchHelper.RIGHT*/){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedRecipe = recipeRecyclerAdapter.recipes.get(layoutPosition)
            viewModel.deleteRecipe(selectedRecipe)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
        val binding = FragmentRecipesBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()


        binding.rvRecipe.adapter= recipeRecyclerAdapter
        binding.rvRecipe.layoutManager=LinearLayoutManager(requireContext())
        recipeRecyclerAdapter.setOnItemClickListener {
/*
            Toast.makeText(requireContext(), "id=${it}", Toast.LENGTH_SHORT).show()
*/
            val action = RecipeFragmentDirections.actionRecipeFragmentToRecipeDetailsFragment2(it)
            findNavController().navigate(action)
        }

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.rvRecipe)
        binding.fab.setOnClickListener {
            findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToRecipeDetailsFragment(0))
        }

    }

    private fun subscribeToObservers(){
        viewModel.recipeList.observe(viewLifecycleOwner, Observer{
            recipeRecyclerAdapter.recipes=it
        })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}