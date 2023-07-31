package com.example.house_analysis.network.api.requests.repos

import android.util.Log
import com.example.house_analysis.network.api.requests.RequestProvider
import com.example.house_analysis.network.model.response.TaskWithSubtasks
import com.example.house_analysis.network.model.response.TasksResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GetRequestsRepo(private val networkRepository: RequestProvider) {
    private val logTag = "Network"

    suspend fun getTasks(): ArrayList<TasksResponse> {
        return suspendCoroutine { continuation ->
            networkRepository.getTasks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        result.forEach {
                            Log.d(logTag, it.toString())
                        }
                        continuation.resume(result)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun getTask(taskId: Long): TasksResponse {
        return suspendCoroutine { continuation ->
            networkRepository.getTask(taskId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        continuation.resume(result)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasks {
        return suspendCoroutine { continuation ->
            networkRepository.getTaskWithSubtasks(taskId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d(logTag, result.toString())
                        continuation.resume(result)
                    }, { error ->
                        Exception(error).printStackTrace()
                        continuation.resumeWithException(error)
                    }
                )
        }
    }
}
