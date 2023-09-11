package com.example.house_analysis.data.model.request

data class UserRegisterModel(
    val fullname: String,
    val email: String,
    val password: String,
    val gender: String,
    val birthday: String,
    val phone: String
)