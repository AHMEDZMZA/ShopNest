package com.example.ecommerce.data.model

data class SignUpRequest(
    val email: String,
    val name: String,
    val password: String,
    val phone: String
)