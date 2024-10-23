package com.example.ecommerce.data.model

data class DataXX(
    val cart_items: List<CartItem>,
//    val sub_total: Int,
//    val total: Int
    val sub_total: Double,
    val total: Double
)