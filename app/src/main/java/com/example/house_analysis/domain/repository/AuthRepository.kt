package com.example.house_analysis.domain.repository

import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import com.example.house_analysis.data.model.response.TokenResponse

interface AuthRepository {

    suspend fun login(userInfo: UserLoginModel): TokenResponse

    suspend fun registration(userInfo: UserRegisterModel): Int

}