package com.example.house_analysis.domain

import com.example.house_analysis.data.repository.RequestRepository
import com.example.house_analysis.data.model.response.TaskWithSubtasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubtaskDataHelper {
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