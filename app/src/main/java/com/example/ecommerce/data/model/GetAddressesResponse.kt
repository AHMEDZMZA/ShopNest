package com.example.ecommerce.data.model

data class GetAddressesResponse(
    val current_page: Int,
//    val `data`: List<Any>,
    val `data`: List<Addresses>,
    val first_page_url: String,
    val from: Any,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: Any,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Any,
    val total: Int
)