package com.example.house_analysis.data.api.requests.repos

import android.util.Log
import com.example.house_analysis.data.api.ApiService
import com.example.house_analysis.data.api.requests.RequestProvider
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PostRequestsRepo(private val networkRepository: RequestProvider) {
    private val logTag = "Network"

    suspend fun login(userInfo: UserLoginModel): Boolean {
        return suspendCoroutine { continuation ->
            networkRepository.loginUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        ApiService.token = result.token
                        Log.d(logTag, result.toString())
                        continuation.resume(true)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun registration(userInfo: UserRegisterModel): Int {
        return suspendCoroutine { continuation ->
            networkRepository.registerUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        continuation.resume(result.code())
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun createTask(taskInfo: TaskRequestModel): Long {
        return suspendCoroutine { continuation ->
            networkRepository.createTask(taskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        continuation.resume(result.taskId)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }
}