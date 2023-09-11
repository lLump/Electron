package com.example.house_analysis.domain.repository

import com.example.house_analysis.data.model.request.auth.UserLoginModel
import com.example.house_analysis.data.model.request.auth.UserRegisterModel

interface AuthRepository {

    suspend fun login(userInfo: UserLoginModel): Boolean

    suspend fun registration(userInfo: UserRegisterModel): Int

}