package com.example.house_analysis.domain.usecase.auth

import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.provider.RepositoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import retrofit2.HttpException

class LoginUseCase {
    private val authRepo = RepositoryProvider.provideAuthRepository()

    suspend operator fun invoke(userInfo: UserLoginModel): Boolean {
        return try {
            authRepo.login(userInfo)
        } catch (e: HttpException) {
            e.stackTraceToString()
            false
        } catch (e: IOException) {
            e.stackTraceToString()
            false
        }
    }
}