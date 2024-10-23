package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Categories
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _categories = MutableStateFlow<Categories?>(null)
    val categories: StateFlow<Categories?> get() = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                _categories.value = response.body()
            }
        }
    }
}
