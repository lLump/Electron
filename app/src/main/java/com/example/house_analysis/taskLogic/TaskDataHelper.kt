package com.example.house_analysis.taskLogic

import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.response.TasksResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDataHelper {
    private val networkRepository = RequestRepository

//    fun getAllTasks() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val tasks = networkRepository.getTasks()
//            withContext(Dispatchers.Main) {
//                rAdapter.dataTransfer.reloadList(tasks)
//            }
//        }
//    }

    fun getAllTasks(callback: (ArrayList<TasksResponse>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = networkRepository.getTasks()
            withContext(Dispatchers.Main) {
                callback(tasks)
            }
        }
    }

    fun deleteTask(id: Int) {
        networkRepository.deleteTask(id)
    }

}