package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Banners
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannersViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _banners = MutableStateFlow<Banners?>(null)
    val banners: StateFlow<Banners?> get() = _banners

    init {
        getBanners()
    }

    fun getBanners() {
        viewModelScope.launch {
            val response = apiService.getBanners()
            if (response.isSuccessful) {
                _banners.value = response.body()
            }
        }
    }
}