package com.fxzly.bakeryinventorymanagement.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipe_id: Int = 0,
    val recipe_name: String,
    val recipe_desc: String,
    val recipe_img: String,
)