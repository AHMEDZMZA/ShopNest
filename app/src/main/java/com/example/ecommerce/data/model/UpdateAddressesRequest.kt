package com.example.ecommerce.data.model

data class UpdateAddressesRequest(
    val city: String,
    val details: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val notes: String,
    val region: String
)