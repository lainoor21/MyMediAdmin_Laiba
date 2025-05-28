package com.fuchsia.mymedicaladmin.model

data class ProductResponseItem(
    val category: String,
    val id: Int,
    val price: Float,
    val product_id: String,
    val product_name: String,
    val stock: Int
)