package com.example.house_analysis.network.api.requests.repos

import android.util.Log
import com.example.house_analysis.network.api.requests.RequestProvider
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeleteRequestsRepo(private val networkRepository: RequestProvider) {
    private val logTag = "Network"

    fun deleteTask(taskId: Int) {
        networkRepository.deleteTask(taskId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    // Выполняется при подписке на операцию
                }

                override fun onComplete() {
                    Log.d(logTag, "Task with ID: $taskId successfully deleted")
                }

                override fun onError(e: Throwable) {
                    Log.d(logTag, "Task with ID: $taskId wasn't deleted")
                }
            })
    }
}