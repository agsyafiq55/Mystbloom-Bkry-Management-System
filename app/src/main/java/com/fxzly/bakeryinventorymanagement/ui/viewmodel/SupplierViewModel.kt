package com.fxzly.bakeryinventorymanagement.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import com.fxzly.bakeryinventorymanagement.data.repository.SupplierRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SupplierViewModel(
    application: Application,
    private val supplierRepository: SupplierRepository
) : AndroidViewModel(application) {

    val suppliers: StateFlow<List<Supplier>> = supplierRepository.getAllSuppliers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertSupplier(supplier: Supplier) {
        viewModelScope.launch {
            supplierRepository.insertSupplier(supplier)
        }
    }

    fun updateSupplier(supplier: Supplier) {
        viewModelScope.launch {
            supplierRepository.updateSupplier(supplier)
        }
    }

    fun deleteSupplier(supplier: Supplier) {
        viewModelScope.launch {
            supplierRepository.deleteSupplier(supplier)
        }
    }

    fun getSupplierById(supplierId: Int): Flow<Supplier?> = supplierRepository.getSupplierById(supplierId)
}