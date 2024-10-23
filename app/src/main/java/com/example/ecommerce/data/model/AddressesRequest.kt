package com.example.ecommerce.data.model

data class AddressesRequest(
    val city: String,
    val details: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val notes: String,
    val region: String
)