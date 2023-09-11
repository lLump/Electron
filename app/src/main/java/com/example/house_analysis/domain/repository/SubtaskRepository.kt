package com.example.house_analysis.domain.repository

import com.example.house_analysis.data.model.request.SubtaskDto
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse

interface SubtaskRepository {

    suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasksResponse

    suspend fun editSubtask(taskId: Long, subtask: SubtaskDto)
}