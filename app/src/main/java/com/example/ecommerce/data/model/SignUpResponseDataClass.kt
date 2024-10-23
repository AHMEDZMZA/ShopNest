package com.example.ecommerce.data.model

data class SignUpResponseDataClass(
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val token: String
)