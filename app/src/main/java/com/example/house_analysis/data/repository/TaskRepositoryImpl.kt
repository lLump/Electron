package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.service.TaskApi
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.response.Task
import com.example.house_analysis.data.model.response.subtasks.TaskWithSubtasksResponse
import com.example.house_analysis.data.model.response.full_task.FullTaskResponse
import com.example.house_analysis.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val service: TaskApi): TaskRepository {
    private val logTag = "Network"

    override suspend fun createTask(taskInfo: TaskRequestModel): FullTaskResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.createTask(taskInfo)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    override suspend fun deleteTask(taskId: Long) {
        withContext(Dispatchers.IO) {
            try {
                service.deleteTask(taskId)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    override suspend fun getTask(taskId: Long): FullTaskResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.getTask(taskId)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    override suspend fun getTasks(): ArrayList<Task> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getTasks()
                ArrayList(response.tasks)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    override suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasksResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.getTaskWithSubtasks(taskId)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

//    suspend fun replaceFloorAndLoungeForSubtask(
//        taskIdToChange: Long,
//        floors: Int,
//        lounges: Int,
//        subtaskIds: List<Long>
//    ): Observable<Response<Unit>> {
//        return service.replaceFloorAndLoungeForSubtask(
//            taskIdToChange,
//            LoungeFloorModel(floors, lounges, subtaskIds)
//        )
//    }

}