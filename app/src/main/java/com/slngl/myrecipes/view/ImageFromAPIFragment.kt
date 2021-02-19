package com.slngl.myrecipes.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.slngl.myrecipes.R
import com.slngl.myrecipes.adapter.ImageRecyclerAdapter
import com.slngl.myrecipes.databinding.FragmentImageApiBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageFromAPIFragment @Inject constructor(private val imageRecyclerAdapter: ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

    private var fragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding
    }
}