package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Profile
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
) : ViewModel() {

    private val _profileState = MutableStateFlow<Profile?>(null)
    val profileState: StateFlow<Profile?> get() = _profileState

    fun getProfile(onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getProfile()
                if (response.isSuccessful && response.body()?.status == true) {
                    _profileState.value = response.body()
                } else {
                    val errorMessage = response.body()?.message ?: "Unknown error"
                    onError(errorMessage.toString())
                }
            } catch (e: Exception) {
                onError("An unexpected error occurred: ${e.message}")
            }
        }
    }

}
