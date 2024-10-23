package com.example.ecommerce.data.model

data class LoginResponse(
    val `data`: LoginResponseDataClass,
    val message: String,
    val status: Boolean
)