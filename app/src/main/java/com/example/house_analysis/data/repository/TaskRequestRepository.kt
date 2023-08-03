package com.example.house_analysis.data.repository

import android.util.Log
import com.example.house_analysis.data.api.service.TaskApi
import com.example.house_analysis.data.model.request.LoungeFloorModel
import com.example.house_analysis.data.model.request.TaskRequestModel
import com.example.house_analysis.data.model.response.TaskWithSubtasks
import com.example.house_analysis.data.model.response.TasksResponse
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TaskRequestRepository(private val service: TaskApi) {
    private val logTag = "Network"

    suspend fun createTask(taskInfo: TaskRequestModel): TasksResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.createTask(taskInfo)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    fun deleteTask(taskId: Long): Completable {
        return service.deleteTask(taskId)
    }

    suspend fun getTask(taskId: Long): TasksResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.getTask(taskId)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    suspend fun getTasks(): ArrayList<TasksResponse> {
        return withContext(Dispatchers.IO) {
            try {
                service.getTasks()
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    suspend fun getTaskWithSubtasks(taskId: Long): TaskWithSubtasks {
        return withContext(Dispatchers.IO) {
            try {
                service.getTaskWithSubtasks(taskId)
            } catch (error: Exception) {
                Log.e(logTag, error.stackTraceToString())
                throw error
            }
        }
    }

    fun replaceFloorAndLoungeForSubtask(
        taskIdToChange: Long,
        floors: Int,
        lounges: Int,
        subtaskIds: List<Long>
    ): Observable<Response<Unit>> {
        return service.replaceFloorAndLoungeForSubtask(
            taskIdToChange,
            LoungeFloorModel(floors, lounges, subtaskIds)
        )
    }

}