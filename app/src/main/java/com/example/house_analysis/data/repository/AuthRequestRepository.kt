package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.api.service.AuthApi
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRequestRepository(private val service: AuthApi) {
    private val logTag = "Network"

    suspend fun login(userInfo: UserLoginModel): Boolean {
        return suspendCoroutine { continuation ->
            service.loginUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        ApiFactory.token = result.token
                        Log.d(logTag, result.toString())
                        continuation.resume(true)
                    }, { error ->
                        Log.e(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun registration(userInfo: UserRegisterModel): Int {
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