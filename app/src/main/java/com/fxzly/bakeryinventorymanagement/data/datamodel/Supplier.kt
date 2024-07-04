package com.fxzly.bakeryinventorymanagement.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class Supplier(
    @PrimaryKey(autoGenerate = true)
    val supplier_id: Int = 0,
    val supplier_name: String,
    val owner_name: String,
    val supplier_contact_no: String,
    val supplier_desc: String,
    val supplier_address: String
)