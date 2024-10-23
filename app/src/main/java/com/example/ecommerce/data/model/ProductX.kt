package com.example.ecommerce.data.model

data class ProductX(
    val discount: Int,
    val id: Int,
    val image: String,
  //  val old_price: Int,
//    val price: Int
    val old_price: Double,
    val price: Double)