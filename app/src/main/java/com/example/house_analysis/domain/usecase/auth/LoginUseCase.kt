package com.example.house_analysis.domain.usecase.auth

import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.provider.RepositoryProvider
import kotlinx.io.IOException
import retrofit2.HttpException

class LoginUseCase {
    private val authRepo = RepositoryProvider.provideAuthRepository()

    suspend operator fun invoke(userInfo: UserLoginModel): Boolean {
        return try {
            val response = authRepo.login(userInfo)
            ApiFactory.token = response.token
            true
        } catch (e: HttpException) {
            e.stackTraceToString()
            false
        } catch (e: IOException) {
            e.stackTraceToString()
            false
        }
    }
}