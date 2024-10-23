package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _product = MutableStateFlow<Products?>(null)
    val product : StateFlow<Products?> get() = _product


    init {
        getProducts()
    }

    fun getProducts(){
        viewModelScope.launch {
            val response = apiService.getAllProducts()
            if(response.isSuccessful){
                _product.value = response.body()
            }
        }
    }
}

