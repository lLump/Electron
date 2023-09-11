package com.example.house_analysis.data.repository

import com.example.house_analysis.data.provider.RepositoryProvider
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.request.auth.UserRegisterModel

object RequestRepository {
    private val taskRepo = RepositoryProvider.provideTaskRepository()
    private val authRepo = RepositoryProvider.provideAuthRepository()

//    private val postRepo = PostRequestsRepo(taskRepo)
//    private val deleteRepo = DeleteRequestsRepo(taskRepo)

//    suspend fun login(userInfo: UserLoginModel) = authRepo.login(userInfo)
    suspend fun register(userInfo: UserRegisterModel) = authRepo.registration(userInfo)
    suspend fun getTasks() = taskRepo.getTasks()
    suspend fun getTask(taskId: Long) = taskRepo.getTask(taskId)
    suspend fun getTaskWithSubtasks(taskId: Long) = taskRepo.getTaskWithSubtasks(taskId)
    suspend fun createTask(taskInfo: TaskRequestModel) = taskRepo.createTask(taskInfo)
    suspend fun deleteTask(taskId: Long) = taskRepo.deleteTask(taskId)
}