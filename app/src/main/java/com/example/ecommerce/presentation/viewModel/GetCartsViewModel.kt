package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddCartRequest
import com.example.ecommerce.data.model.AddOrDeleteCartsResponse
import com.example.ecommerce.data.model.GetCarts
import com.example.ecommerce.data.model.UpdateCart
import com.example.ecommerce.data.model.addCartsOrDeleteCartsDataClassRequest
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class GetCartsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _carts = MutableStateFlow<GetCarts?>(null)
    val carts: StateFlow<GetCarts?> get() = _carts

    private val _addOrDeleteCarts = MutableStateFlow<AddOrDeleteCartsResponse?>(null)
    val addCarts: StateFlow<AddOrDeleteCartsResponse?> get() = _addOrDeleteCarts

    private val _updateCarts = MutableStateFlow<UpdateCart?>(null)
    val updateCarts: StateFlow<UpdateCart?> get() = _updateCarts

    init {
        getCarts()
    }

    fun getCarts() {
        viewModelScope.launch {
            try {
                val response = apiService.getCarts()
                if (response.isSuccessful) {
                    _carts.value = response.body()
                } else {
                    handleApiError("Error fetching carts", response.errorBody()?.string())
                }
            } catch (e: Exception) {
                Log.e("GetCartsViewModel", "Exception: ${e.message}", e)
            }
        }
    }

    fun addCartsOrDeleteCarts(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = addCartsOrDeleteCartsDataClassRequest(product_id = productId)
                val response = apiService.addCartsOrDeleteCarts(request)
                if (response.isSuccessful) {
                    _addOrDeleteCarts.value = response.body()
                    onSuccess("Cart updated successfully.")
                } else {
                    handleApiError("Failed to add or delete cart", response.errorBody()?.string())
                    onError("Failed to add or delete cart.")
                }
            } catch (e: Exception) {
                handleException(e, onError)
            }
        }
    }

    fun updateCarts(
        productId: Int,
        quantity: Int,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.updateCarts(productId, AddCartRequest(quantity = quantity))
                if (response.isSuccessful) {
                    getCarts()
                    onSuccess()
                } else {
                    handleApiError("Failed to update cart", response.errorBody()?.string())
                    onError("Failed to update cart.")
                }
            } catch (e: Exception) {
                handleException(e, onError)
            }
        }
    }

    // Helper to handle errors
    private fun handleApiError(logMessage: String, errorBody: String?) {
        _carts.value = null
    }

    // Helper to handle exceptions
    private fun handleException(e: Exception, onError: (String) -> Unit) {
        onError("Something went wrong. Please try again.")
    }
}
