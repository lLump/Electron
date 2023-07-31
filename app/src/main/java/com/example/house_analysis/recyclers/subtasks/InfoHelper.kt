package com.example.house_analysis.recyclers.subtasks

import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.response.TaskWithSubtasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoHelper {
    private val networkRepository = RequestRepository
    fun getTaskWithSubtasks(taskId: Long, callback: (TaskWithSubtasks) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = networkRepository.getTaskWithSubtasks(taskId)
            withContext(Dispatchers.Main) {
                callback(task)
            }
        }
    }

}