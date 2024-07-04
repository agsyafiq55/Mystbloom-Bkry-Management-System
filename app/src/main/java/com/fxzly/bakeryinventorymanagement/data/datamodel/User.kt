package com.fxzly.bakeryinventorymanagement.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int = 0,
    val username: String,
    val password: String,
    val role: String,
    val first_name: String,
    val last_name: String,
    val contact_no: String,
    val user_img: String
)