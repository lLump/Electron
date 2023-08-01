package com.example.house_analysis.data.api.requests

import com.example.house_analysis.data.api.ApiService
import com.example.house_analysis.data.model.request.LoungeFloorModel
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.request.UserLoginModel
import com.example.house_analysis.data.model.request.UserRegisterModel
import com.example.house_analysis.data.model.response.TaskWithSubtasks
import com.example.house_analysis.data.model.response.TasksResponse
import com.example.house_analysis.data.model.response.TokenResponse
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Response

class RequestProvider(private val apiService: ApiService) {

    fun loginUser(userInfo: UserLoginModel): Observable<TokenResponse> {
        return apiService.loginUser(userInfo)
    }

    fun registerUser(userInfo: UserRegisterModel): Observable<Response<Unit>> {
        return apiService.registerUser(userInfo)
    }

    fun createTask(taskInfo: TaskRequestModel): Observable<TasksResponse> {
        return apiService.createTask(taskInfo)
    }

    fun deleteTask(taskId: Long): Completable {
        return apiService.deleteTask(taskId)
    }

    fun getTasks(): Observable<ArrayList<TasksResponse>> {
        return apiService.getUserTasks()
    }

    fun getTask(taskId: Long): Observable<TasksResponse> {
        return apiService.getTask(taskId)
    }

    fun getTaskWithSubtasks(taskId: Long): Observable<TaskWithSubtasks> {
        return apiService.getTaskWithSubtasks(taskId)
    }

    fun replaceFloorAndLoungeForSubtask(taskIdToChange: Long, floors: Int, lounges: Int, subtaskIds: List<Long>): Observable<Response<Unit>> {
        return apiService.replaceFloorAndLoungeForSubtask(taskIdToChange, LoungeFloorModel(floors, lounges, subtaskIds))
    }

}