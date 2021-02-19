package com.slngl.myrecipes.network

import com.slngl.myrecipes.network.AppConstants.API_KEY
import com.slngl.myrecipes.network.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ) : Response<ImageResponse>
}