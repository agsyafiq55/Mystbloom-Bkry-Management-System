package com.fxzly.bakeryinventorymanagement.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val product_id: Int = 0,
    val product_name: String,
    val product_desc: String,
    val product_price: Float,
    val product_stock: Int,
    val product_img: String
)