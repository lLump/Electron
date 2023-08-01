package com.example.house_analysis.data.api.requests

import com.example.house_analysis.data.api.ApiService

object RequestRepositoryProvider {
    fun provideRequestRepository(): RequestProvider {
        return RequestProvider(ApiService.create())
    }

    //TODO("Separate get/post/delete providers")

}