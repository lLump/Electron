package com.example.house_analysis.taskLogic.tasks.model.view

import com.example.house_analysis.network.model.response.TasksResponse

sealed class TaskList {
    data class TaskItem(val task: TasksResponse) : TaskList()
    data class AdditionalItem(val task: TasksResponse) : TaskList()
}