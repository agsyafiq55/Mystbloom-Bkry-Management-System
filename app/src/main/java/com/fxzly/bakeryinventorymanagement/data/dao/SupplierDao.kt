package com.fxzly.bakeryinventorymanagement.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import com.fxzly.bakeryinventorymanagement.data.datamodel.User
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplier(supplier: Supplier)

    @Update
    suspend fun updateSupplier(supplier: Supplier)

    @Delete
    suspend fun deleteSupplier(supplier: Supplier)

    @Query("SELECT * FROM suppliers WHERE supplier_id = :supplierId")
    fun getSupplierById(supplierId: Int): Flow<Supplier>

    @Query("SELECT * FROM suppliers")
    fun getAllSuppliers(): Flow<List<Supplier>>

    @Query("SELECT * FROM suppliers WHERE supplier_name LIKE '%' || :query || '%'")
    fun searchSuppliersByName(query: String): Flow<List<Supplier>>
}