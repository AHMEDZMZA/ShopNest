package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.GetContactsDataClass
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _contacts = MutableStateFlow<GetContactsDataClass?>(null)
    val contacts: StateFlow<GetContactsDataClass?> get() = _contacts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun getContacts(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true  // Start loading
            try {
                val response = apiService.getContacts()
                if (response.isSuccessful && response.body()?.status == true) {
                    _contacts.value = response.body()
                    onSuccess()
                } else {
                    val errorMessage = response.body()?.message ?: "Unknown error"
                    onError(errorMessage.toString())
                    Log.e("ContactsViewModel", "Error fetching contacts: $errorMessage")
                }
            } catch (e: Exception) {
                onError("An unexpected error occurred: ${e.message}")
                Log.e("ContactsViewModel", "Exception: ${e.message}", e)
            } finally {
                _isLoading.value = false  // Stop loading
            }
        }
    }
}
