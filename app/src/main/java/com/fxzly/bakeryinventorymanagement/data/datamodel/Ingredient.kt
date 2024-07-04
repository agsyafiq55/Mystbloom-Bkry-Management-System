package com.fxzly.bakeryinventorymanagement.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredient_id: Int = 0,
    val ingredient_name: String,
    val ingredient_desc: String,
    val ingredient_price: Float,
    val ingredient_stock: Int,
    val ingredient_img: String
)