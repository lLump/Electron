package com.example.house_analysis.data.model.response

import com.example.house_analysis.data.model.response.full_task.Creator
import com.example.house_analysis.data.model.response.full_task.Mark
import com.example.house_analysis.presentation.recyclers.tasks.model.TaskInfo

data class TasksResponse(
    val tasks: List<Task>,
    val totalTasks: Int
)

data class Task(
    val floorsFrom: Int,
    val floorsTo: Int,
    val loungesFrom: Int,
    val loungesTo: Int,
    val subtasksFrom: Int,
    val subtasksTo: Int,
    val taskId: Long,
    val title: String
)

fun Task.toTaskInfo(): TaskInfo {
    return TaskInfo(
        floorsFrom = floorsFrom,
        floorsTo = floorsTo,
        loungesFrom = loungesFrom,
        loungesTo = loungesTo,
        subtasksFrom = subtasksFrom,
        subtasksTo = subtasksTo,
        taskId = taskId,
        title = title,

        //Additional info
        priority = "",
        creator = Creator("", "", ""),
        description = "",
        marks = listOf<Mark>()
    )
}