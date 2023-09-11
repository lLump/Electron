package com.example.house_analysis.domain.usecase.subtask

import com.example.house_analysis.data.model.request.SubtaskDto
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse
import com.example.house_analysis.data.provider.RepositoryProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import retrofit2.HttpException

class SubtasksInterceptor {
    private val repository = RepositoryProvider.provideSubtaskRepository()

    fun getTaskWithSubtasks(taskId: Long, callback: (TaskWithSubtasksResponse) -> Unit) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.getTaskWithSubtasks(taskId)
                withContext(Dispatchers.Main) {
                    callback(response)
                }
            }
        } catch (e: HttpException) {
            e.stackTraceToString()
            throw e
        } catch (e: IOException) {
            e.stackTraceToString()
            throw e
        }
    }

    fun editSubtask(subtaskId: Long, subtask: SubtaskDto, callback: (Boolean) -> Unit) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.editSubtask(subtaskId, subtask)
                withContext(Dispatchers.Main) {
                    callback(true)
                }
            }
        } catch (e: HttpException) {
            e.stackTraceToString()
            throw e
        } catch (e: IOException) {
            e.stackTraceToString()
            throw e
        }
    }
}