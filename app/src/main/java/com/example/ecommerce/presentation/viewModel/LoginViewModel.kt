package com.example.ecommerce.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.data.model.LoginResponse
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> get() = _loginResponse


    fun login(loginRequest: LoginRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.login(loginRequest)
                if (response.body()?.data!=null) {
                    _loginResponse.value = response.body()
                    authInterceptor.setToken(response.body()?.data?.token?:"")
                    sharedPreferences.edit().putString("token", response.body()?.data?.token).apply()
                    // Save token to SharedPreferences if needed
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