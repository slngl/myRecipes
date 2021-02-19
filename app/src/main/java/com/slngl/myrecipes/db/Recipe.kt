package com.slngl.myrecipes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
        val name: String,
        val yield: String,
        val time: String,
        val method: String,
        val ingredients: String,
        val imageURL: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null
)
