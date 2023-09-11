package com.example.house_analysis.data.api

import com.example.house_analysis.data.api.service.AuthApi
import com.example.house_analysis.data.api.service.SubtaskApi
import com.example.house_analysis.data.api.service.TaskApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    var token = ""

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.electronos.ru/api/v1/")
        .build()

    fun createTaskApi(): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }

    fun createAuthApi(): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    fun createSubtaskApi(): SubtaskApi {
        return retrofit.create(SubtaskApi::class.java)
    }
}