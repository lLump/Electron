package com.example.house_analysis.domain.usecase.auth

import com.example.house_analysis.data.model.request.auth.UserLoginModel
import com.example.house_analysis.data.provider.RepositoryProvider
import kotlinx.io.IOException
import retrofit2.HttpException

class LoginUseCase {
    private val repository = RepositoryProvider.provideAuthRepository()

    suspend operator fun invoke(userInfo: UserLoginModel): Boolean {
        return try {
            repository.login(userInfo)
        } catch (e: HttpException) {
            e.stackTraceToString()
            false
        } catch (e: IOException) {
            e.stackTraceToString()
            false
        }
    }
}