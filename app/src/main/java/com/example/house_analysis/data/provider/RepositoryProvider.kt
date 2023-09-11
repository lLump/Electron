package com.example.house_analysis.data.provider

import com.example.house_analysis.data.api.ApiFactory
import com.example.house_analysis.data.repository.AuthRepositoryImpl
import com.example.house_analysis.data.repository.SubtaskRepositoryImpl
import com.example.house_analysis.data.repository.TaskRepositoryImpl
import com.example.house_analysis.domain.repository.AuthRepository
import com.example.house_analysis.domain.repository.SubtaskRepository
import com.example.house_analysis.domain.repository.TaskRepository

object RepositoryProvider {

    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(ApiFactory.createAuthApi())
    }

    fun provideTaskRepository(): TaskRepository {
        return TaskRepositoryImpl(ApiFactory.createTaskApi())
    }

    fun provideSubtaskRepository(): SubtaskRepository {
        return SubtaskRepositoryImpl(ApiFactory.createSubtaskApi())
    }

}