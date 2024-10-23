package com.example.ecommerce.presentation.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.ChangeProfileRequest
import com.example.ecommerce.data.model.Profile
import com.example.ecommerce.data.network.ApiService
import com.example.ecommerce.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _changeProfileResponse = MutableStateFlow<Profile?>(null)
    val changeProfileResponse: StateFlow<Profile?> get() = _changeProfileResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun changeProfile(
        name: String,
        email: String,
        phone: String,
        image: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getUpdateProfile(ChangeProfileRequest(email, image, name, phone))
                Log.d("ChangeProfileViewModel", "Response: ${response.body()}")
                if (response.isSuccessful && response.body()?.status == true) {
                    _changeProfileResponse.value = response.body()
                    authInterceptor.setToken(response.body()?.data?.token ?: "")
                    sharedPreferences.edit().putString("token", response.body()?.data?.token).apply()
                    onSuccess()
                } else {
                    val errorMessage = response.body()?.message ?: "Unknown error"
                    onError(errorMessage.toString())
                }
            } catch (e: Exception) {
                onError("An unexpected error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
