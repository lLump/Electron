package com.example.house_analysis.data.provider

import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.repository.AuthRepositoryImpl
import com.example.house_analysis.data.repository.TaskRepositoryImpl
import com.example.house_analysis.domain.repository.AuthRepository

object RepositoryProvider {

    fun provideTaskProvider(): TaskRepositoryImpl {
        return TaskRepositoryImpl(ApiFactory.createTaskApi())
    }

    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(ApiFactory.createAuthApi())
    }

}