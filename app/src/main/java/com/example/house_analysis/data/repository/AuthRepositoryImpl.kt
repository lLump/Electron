package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.api.service.AuthApi
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import com.example.house_analysis.data.model.response.TokenResponse
import com.example.house_analysis.domain.repository.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl(private val service: AuthApi) : AuthRepository {
    private val logTag = "Network"

    override suspend fun login(userInfo: UserLoginModel): Boolean {
        return suspendCoroutine { continuation ->
            service.loginUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        ApiFactory.token = result.token
                        continuation.resume(true)
                    }, { error ->
                        Log.e(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

//    override suspend fun login(userInfo: UserLoginModel): TokenResponse {
//        return try {
//            val response = withContext(Dispatchers.IO) {
//                service.loginUser(userInfo)
//            }
//            if (response.isSuccessful) {
//                ApiFactory.token = response.body()?.token ?: ""
//            }
//
//        } catch (e: Exception) {
//            Log.e(logTag, e.stackTraceToString())
//            throw e
//        }
//    }

    //    override suspend fun registration(userInfo: UserRegisterModel): Int {
//        return suspendCoroutine { continuation ->
//            service.registerUser(userInfo)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                    { result ->
//                        Log.d(logTag, result.toString())
//                        continuation.resume(result.code())
//                    }, { error ->
//                        Log.e(logTag, error.stackTraceToString())
//                        continuation.resumeWithException(error)
//                    }
//                )
//        }
//    }
    override suspend fun registration(userInfo: UserRegisterModel): Int {
        return try {
            val response = withContext(Dispatchers.IO) {
                service.registerUser(userInfo)
            }
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
            } else {
                Log.e(logTag, "Registration failed. Status code: ${response.code()}")
            }
            response.code()
        } catch (e: Exception) {
            Log.e(logTag, e.stackTraceToString())
            throw e
        }
    }

}