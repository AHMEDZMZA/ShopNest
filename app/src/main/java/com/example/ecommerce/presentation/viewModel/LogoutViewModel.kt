package com.example.ecommerce.presentation.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Logout
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _logoutResponse = MutableStateFlow<Logout?>(null)
    val logoutResponse: StateFlow<Logout?> get() = _logoutResponse

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit)
    {
        viewModelScope.launch {
            try {
                val response = apiService.getLogout()
                if (response.isSuccessful && response.body()?.status == true) {
                    _logoutResponse.value = response.body()
                    authInterceptor.setToken("")
                    sharedPreferences.edit().remove("token").apply()
                    sharedPreferences.edit().remove("password").apply()
                    Log.d("LogoutViewModel", " zamzam ${sharedPreferences.getString("token", "")}")
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