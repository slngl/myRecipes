package com.slngl.myrecipes.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.slngl.myrecipes.R
import com.slngl.myrecipes.db.RecipeDao
import com.slngl.myrecipes.db.RecipeDatabase
import com.slngl.myrecipes.di.qualifiers.BaseUrlQualifier
import com.slngl.myrecipes.network.PixabayAPI
import com.slngl.myrecipes.repository.IRecipeRepository
import com.slngl.myrecipes.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, RecipeDatabase::class.java, "RecipesDB"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipeDatabase) = database.recipeDao()

    @Singleton
    @Provides
    fun provideNormalRepo(dao: RecipeDao, api: PixabayAPI) =
        RecipeRepository(dao, api) as IRecipeRepository

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

    @Singleton
    @Provides
    @BaseUrlQualifier
    fun provideBaseUrl(): String = "http://pixabay.com"


    @Singleton
    @Provides
    fun provideApiService(
        @BaseUrlQualifier baseUrl: String,
    ): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(PixabayAPI::class.java)
    }


}