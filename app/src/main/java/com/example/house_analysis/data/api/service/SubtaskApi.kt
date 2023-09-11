package com.example.house_analysis.data.api.service

import com.example.house_analysis.data.model.request.SubtaskDto
import com.example.house_analysis.data.model.response.subtasks.EditedSubtaskResponse
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SubtaskApi {
    @GET("tasks/{taskId}/subtasks")
    suspend fun getTaskWithSubtasks(@Path("taskId") taskId: Long): TaskWithSubtasksResponse

    @PATCH("tasks/subtasks/{subtaskId}")
    suspend fun editSubtask(@Path("subtaskId") subtaskId: Long, @Body subtask: SubtaskDto): EditedSubtaskResponse
}