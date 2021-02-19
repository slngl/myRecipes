package com.slngl.myrecipes.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.slngl.myrecipes.R
import com.slngl.myrecipes.databinding.FragmentRecipeDetailsBinding
import com.slngl.myrecipes.network.model.Status
import com.slngl.myrecipes.viewModel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailsFragment @Inject constructor(val glide: RequestManager) :
    Fragment(R.layout.fragment_recipe_details) {

    lateinit var viewModel: RecipeViewModel
    private var fragmentBinding: FragmentRecipeDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
        val binding = FragmentRecipeDetailsBinding.bind(view)
        fragmentBinding = binding

        subsribeToObservers()

        binding.ivRecipe.setOnClickListener {
            findNavController().navigate(RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToImageFromAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btSave.setOnClickListener {
            viewModel.makeRecipe(
                binding.nameText.text.toString(),
                binding.etYield.text.toString(),
                binding.etTotalTime.text.toString(),
                binding.tvMethod.text.toString(),
                binding.textViewIngredients.text.toString()
            )
        }

    }

    private fun subsribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.ivRecipe)
            }
        })

        viewModel.insertMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(), "Başarılı", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertRecipeMsg()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), "Tüm alanları doldurmalısınız", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}