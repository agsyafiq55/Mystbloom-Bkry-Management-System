package com.fxzly.bakeryinventorymanagement.data.repository

import com.fxzly.bakeryinventorymanagement.data.dao.SupplierDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import kotlinx.coroutines.flow.Flow

class SupplierRepository(private val supplierDao: SupplierDao) {

    fun getAllSuppliers(): Flow<List<Supplier>> = supplierDao.getAllSuppliers()

    fun getSupplierById(supplierId: Int): Flow<Supplier> = supplierDao.getSupplierById(supplierId)

    suspend fun insertSupplier(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    suspend fun updateSupplier(supplier: Supplier) {
        supplierDao.updateSupplier(supplier)
    }

    suspend fun deleteSupplier(supplier: Supplier) {
        supplierDao.deleteSupplier(supplier)
    }
}



























/*
class SupplierRepository(private val supplierDao: SupplierDao) {
    // Functions to interact with the SupplierDao
    fun getAllSuppliers():Flow<List<Supplier>> = supplierDao.getAllSuppliers()

    suspend fun insertSupplier(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    // ... other functions for updating, deleting, etc.
}*/