package com.example.house_analysis.data.api.service

import com.example.house_analysis.data.model.request.LoungeFloorModel
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.response.TaskWithSubtasks
import com.example.house_analysis.data.model.response.TasksResponse
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {
    @POST("tasks")
    suspend fun createTask(@Body request: TaskRequestModel): TasksResponse

    @GET("tasks/{taskId}")
    suspend fun getTask(@Path("taskId") taskId: Long): TasksResponse

    @GET("tasks/user")
    suspend fun getTasks(): ArrayList<TasksResponse>

    @DELETE("tasks/{taskId}")
    fun deleteTask(@Path("taskId") taskId: Long): Completable

    @PATCH("tasks/{taskId}/subtasks/replace")
    fun replaceFloorAndLoungeForSubtask(@Path("taskId") taskId: Long, @Body request: LoungeFloorModel): Observable<Response<Unit>>

    @GET("tasks/{taskId}/subtasks")
    suspend fun getTaskWithSubtasks(@Path("taskId") taskId: Long): TaskWithSubtasks
}