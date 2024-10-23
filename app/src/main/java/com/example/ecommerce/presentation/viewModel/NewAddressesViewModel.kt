package com.example.ecommerce.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddressesRequest
import com.example.ecommerce.data.model.AddressesResponse
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewAddressesViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences,
    private val authInterceptor: AuthInterceptor
) : ViewModel() {

    private val _newAddressesResponse = MutableStateFlow<AddressesResponse?>(null)
    val newAddressesResponse: StateFlow<AddressesResponse?> get() = _newAddressesResponse

    fun getNewAddresses(addressesRequest: AddressesRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Call the API with the AddressesRequest
                val response = apiService.getNewAddresses(addressesRequest)

                if (response.isSuccessful && response.body()?.status == true) {
                    _newAddressesResponse.value = response.body()
                    onSuccess()
                } else {
                    val errorMessage = response.body()?.message ?: "Unknown error"
                    onError(errorMessage)
                }
            } catch (e: Exception) {
                onError("An unexpected error occurred: ${e.message}")
            }
        }
    }
}
