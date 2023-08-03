package com.example.house_analysis.data.provider

import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.repository.AuthRequestRepository
import com.example.house_analysis.data.repository.TaskRequestRepository

object RepositoryProvider {
    fun provideTaskProvider(): TaskRequestRepository {
        return TaskRequestRepository(ApiFactory.createTaskApi())
    }

    fun provideAuthRepository(): AuthRequestRepository {
        return AuthRequestRepository(ApiFactory.createAuthApi())
    }

}