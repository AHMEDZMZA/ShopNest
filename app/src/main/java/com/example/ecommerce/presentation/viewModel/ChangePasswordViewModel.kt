package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.ChangePasswordRequest
import com.example.ecommerce.data.model.ChangePasswordResponse
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor
) : ViewModel(){

   private val _changePasswordResponse = MutableStateFlow<ChangePasswordResponse?>(null)
    val changePasswordResponse: StateFlow<ChangePasswordResponse?> get() = _changePasswordResponse

    fun changePassword(currentPassword: String, newPassword: String,onSuccess: () -> Unit, onError: (String) -> Unit){
        viewModelScope.launch {
            try {
                val response = apiService.getChangePassword(ChangePasswordRequest(currentPassword, newPassword))
                if (response.isSuccessful && response.body()?.status == true) {
                    _changePasswordResponse.value = response.body()
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