package com.example.house_analysis.data.api.requests

import com.example.house_analysis.data.api.requests.repos.DeleteRequestsRepo
import com.example.house_analysis.data.api.requests.repos.GetRequestsRepo
import com.example.house_analysis.data.api.requests.repos.PostRequestsRepo
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel

object RequestRepository {
    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()

    private val getRepo = GetRequestsRepo(networkRepository)
    private val postRepo = PostRequestsRepo(networkRepository)
    private val deleteRepo = DeleteRequestsRepo(networkRepository)

    suspend fun login(userInfo: UserLoginModel) = postRepo.login(userInfo)
    suspend fun register(userInfo: UserRegisterModel) = postRepo.registration(userInfo)
    suspend fun getTasks() = getRepo.getTasks()
    suspend fun getTask(taskId: Long) = getRepo.getTask(taskId)
    suspend fun getTaskWithSubtasks(taskId: Long) = getRepo.getTaskWithSubtasks(taskId)
    suspend fun createTask(taskInfo: TaskRequestModel) = postRepo.createTask(taskInfo)
    fun deleteTask(taskId: Long) { deleteRepo.deleteTask(taskId) }
}