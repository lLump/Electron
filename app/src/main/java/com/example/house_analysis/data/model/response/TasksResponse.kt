package com.example.house_analysis.data.model.response

import com.example.house_analysis.data.model.response.additional.Creator
import com.example.house_analysis.data.model.response.additional.Mark
import com.example.house_analysis.domain.model.TaskInfo

data class TasksResponse(
    val totalTasks: Int,
    val tasks: List<Task>
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