package com.example.house_analysis.domain.repository

import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.response.Task
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse
import com.example.house_analysis.data.model.response.full_task.FullTaskResponse

interface TaskRepository {

    suspend fun createTask(taskInfo: TaskRequestModel): FullTaskResponse

    suspend fun deleteTask(taskId: Long)

    suspend fun getTask(taskId: Long): FullTaskResponse

    suspend fun getTasks(): ArrayList<Task>

    suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasksResponse

}