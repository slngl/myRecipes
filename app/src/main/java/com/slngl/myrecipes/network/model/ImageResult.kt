package com.slngl.myrecipes.network.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
        val comments: Int? = null,
        val downloads: Int? = null,
        val favorites: Int? = null,
        val id: Int? = null,
        val imageHeight: Int? = null,
        val imageSize: Int? = null,
        val imageWidth : Int? = null,
        val largeImageURL: String? = null,
        val likes: Int? = null,
        val pageURL : String? = null,
        val previewHeight: Int? = null,
        val previewURL: String? = null,
        val previewWidth:Int? = null,
        val tags: String? = null,
        val type: String? = null,
        val user: String? = null,
        @SerializedName("user_id")
        val userId : Int? = null,
        val userImageURL: String? = null,
        val views : Int? = null,
        val webformatHeight: Int? = null,
        val webformatURL: String? = null,
        val webformatWidth: Int  ? = null
)