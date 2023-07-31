package com.example.house_analysis.recyclers.tasks

import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.response.TasksResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataHelper {
    private val networkRepository = RequestRepository
    fun getAllTasks(callback: (ArrayList<TasksResponse>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = networkRepository.getTasks()
            withContext(Dispatchers.Main) {
                callback(tasks)
            }
        }
    }

    fun getTask(taskId: Long, callback: (TasksResponse) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = networkRepository.getTask(taskId)
            withContext(Dispatchers.Main) {
                callback(task)
            }
        }
    }

    fun deleteTask(id: Long) {
        networkRepository.deleteTask(id)
    }

}