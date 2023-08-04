package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.service.AuthApi
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import com.example.house_analysis.data.model.response.TokenResponse
import com.example.house_analysis.domain.repository.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl(private val service: AuthApi): AuthRepository {
    private val logTag = "Network"

    override suspend fun login(userInfo: UserLoginModel): TokenResponse {
        return service.loginUser(userInfo)
    }

    override suspend fun registration(userInfo: UserRegisterModel): Int {
        return suspendCoroutine { continuation ->
            service.registerUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        continuation.resume(result.code())
                    }, { error ->
                        Log.e(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

}