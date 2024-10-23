package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAddressesViewModel@Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    fun deleteAddresses(id: Int, onSuccessful: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getDeleteAddresses(id)
                if (response.isSuccessful) {
                    onSuccessful()
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
