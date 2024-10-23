package com.example.ecommerce.presentation.viewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.GetFavorites
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _favorites = MutableStateFlow<GetFavorites?>(null)
    val favorites: StateFlow<GetFavorites?> get() = _favorites

    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts


    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun favoriteAddOrDelete(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = AddOrDeleteFavoriteRequest(product_id = productId)
                val response = apiService.addOrDeleteFavorites(request)

                if (response.isSuccessful) {
                    _favoriteAddOrDelete.value = response.body()
                    //Refresh the favorites list after updating
                    onSuccess("Favorite updated successfully.")
                } else {
                    _favoriteAddOrDelete.value = null
                    handleError("Failed to add/delete favorite: ${response.message()}")
                    onError("Failed to add or delete favorite.")
                }
            } catch (ex: Exception) {
                _favoriteAddOrDelete.value = null
                handleError("Exception occurred: ${ex.localizedMessage}")
                onError("Exception: ${ex.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun getFavorites() {
        viewModelScope.launch {
            try {
                val response = apiService.getFavorites()
                if (response.isSuccessful) {
                    _favorites.value = response.body()
                } else {
                    _favorites.value = null
                }
            } catch (ex: Exception) {
                _favorites.value = null
            }
        }
    }

    private fun handleError(message: String) {
        Log.e("FavoritesViewModel", message)
        _errorMessage.value = message
    }

}
