package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {

    private val _categoryProducts = MutableStateFlow<Products?>(null)
    val categoryProducts: StateFlow<Products?> get() = _categoryProducts

    private val _allProducts = MutableStateFlow<List<ProductItemSmall>?>(null)
    val allProducts: StateFlow<List<ProductItemSmall>?> get() = _allProducts

     fun getProducts(id:Int) {
            viewModelScope.launch {
                try {
                    val response = apiService.getProductsByCategory(id)
                    if (response.isSuccessful) {
                        _categoryProducts.value = response.body()
                    } else {
                        handleError("Failed to get products by category: ${response.code()}")
                    }
                } catch (ex: Exception) {
                    handleError("Exception occurred while getting products by category: ${ex.localizedMessage}")
                }
            }

    }

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllProduct()
                if (response.isSuccessful) {
                    _allProducts.value = response.body()?.data?.data?.filter { it.in_favorites }
                } else {
                    handleError("Failed to get all products: ${response.code()}")
                }
            } catch (ex: Exception) {
                handleError("Exception occurred while getting all products: ${ex.localizedMessage}")
            }
        }
    }
    private fun handleError(message: String) {
        Log.w("Error", message)
        // Optionally, update UI state to reflect the error if needed.
        _categoryProducts.value = null  // Reset the category products in case of an error
        _allProducts.value = null        // Reset the all products in case of an error
      //  _favoriteAddOrDelete.value = null // Reset the favorite add or delete response
    }
}
