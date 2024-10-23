package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.SignUpRequest
import com.example.ecommerce.data.model.SignUpResponse
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor,
) : ViewModel() {
    private val _signUpResponse = MutableStateFlow<SignUpResponse?>(null)
    fun singUp(signUpRequest: SignUpRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.register(signUpRequest)
                if (response.body()?.data!=null){
                    _signUpResponse.value = response.body()
                    authInterceptor.setToken(response.body()?.data?.token?:"")
                    onSuccess()
                } else {
                    // Handle unsuccessful response
                    val errorMessage = response.body()?.message
                    onError(errorMessage?:"An unexpected error occurred")
                }
            } catch (e: Exception) {
                // Handle exceptions
                onError("An unexpected error occurred")
            }
        }
    }
}
