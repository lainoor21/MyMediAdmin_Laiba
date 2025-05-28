package com.fuchsia.mymedicaladmin.model

data class GetUserModel(
    val address: String,
    val block: Int,
    val date_of_acount_creation: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val name: String,
    val password: String,
    val phone_number: String,
    val pincode: String,
    val user_id: String
)