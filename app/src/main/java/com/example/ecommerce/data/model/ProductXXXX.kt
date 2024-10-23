package com.example.ecommerce.data.model

data class ProductXXXX(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val name: String,
    //val old_price: Int,
    //val price: Int
    val old_price: Double,
    val price: Double
)