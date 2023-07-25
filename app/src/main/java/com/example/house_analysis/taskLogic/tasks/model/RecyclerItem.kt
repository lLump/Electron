package com.example.house_analysis.taskLogic.tasks.model

import com.example.house_analysis.network.model.response.TasksResponse

sealed class RecyclerItem {
    data class TaskItem(val task: TasksResponse) : RecyclerItem()
    data class AdditionalItem(val task: TasksResponse) : RecyclerItem()
}