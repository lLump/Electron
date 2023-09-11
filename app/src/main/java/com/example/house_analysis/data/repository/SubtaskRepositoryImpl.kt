package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.service.SubtaskApi
import com.example.house_analysis.data.model.request.SubtaskDto
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse
import com.example.house_analysis.domain.repository.SubtaskRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SubtaskRepositoryImpl(private val service: SubtaskApi) : SubtaskRepository {
    private val logTag = "Network"

    override suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasksResponse {
        return try {
            service.getTaskWithSubtasks(taskId)
        } catch (e: Exception) {
            Log.e(logTag, e.stackTraceToString())
            throw e
        }
    }

    override suspend fun editSubtask(subtaskId: Long, subtask: SubtaskDto) {
        try {
            service.editSubtask(subtaskId, subtask)
        } catch (e: Exception) {
            Log.e(logTag, e.stackTraceToString())
            throw e
        }
    }


}