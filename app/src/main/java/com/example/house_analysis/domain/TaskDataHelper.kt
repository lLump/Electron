package com.example.house_analysis.domain

import com.example.house_analysis.data.model.response.Task
import com.example.house_analysis.data.repository.RequestRepository
import com.example.house_analysis.data.model.response.full_task.FullTaskResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDataHelper {
    private val networkRepository = RequestRepository
    fun getAllTasks(callback: (ArrayList<Task>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = networkRepository.getTasks()
            withContext(Dispatchers.Main) {
                callback(ArrayList(response))
            }
        }
    }

    fun getTask(taskId: Long, callback: (FullTaskResponse) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = networkRepository.getTask(taskId)
            withContext(Dispatchers.Main) {
                callback(task)
            }
        }
    }

    fun deleteTask(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            networkRepository.deleteTask(id)
        }
    }

}