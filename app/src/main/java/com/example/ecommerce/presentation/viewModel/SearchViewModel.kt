package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.SearchRequest
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _productSearch = MutableStateFlow<Products?>(null)
    val productSearch: StateFlow<Products?> get() = _productSearch

    private val _isLoading = MutableStateFlow(false)  // Add loading state
    val isLoading: StateFlow<Boolean> get() = _isLoading  // Expose it as a StateFlow

    fun getProductsSearch(text: String = "") {
        viewModelScope.launch {
            _isLoading.value = true  // Set loading state to true
            val response = apiService.getSearch(SearchRequest(text))
            _isLoading.value = false  // Set loading state to false

            if (response.isSuccessful) {
                _productSearch.value = response.body()
            } else {
                _productSearch.value = null
            }
        }
    }

    fun clearSearchResults() {
        _productSearch.value = null
    }
}


