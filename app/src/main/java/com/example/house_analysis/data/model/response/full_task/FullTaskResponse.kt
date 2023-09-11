package com.example.house_analysis.data.model.response.full_task

import com.example.house_analysis.data.model.response.additional.Creator
import com.example.house_analysis.data.model.response.additional.Mark
import com.example.house_analysis.data.model.response.additional.Performer
import com.example.house_analysis.domain.model.TaskInfo

data class FullTaskResponse(
    val address: String?,
    val createdOn: String,
    val creator: Creator,
    val performers: List<Performer>,
    val marks: List<Mark>,
    val description: String?,
    val priority: String?,

    val floorsFrom: Int,
    val floorsTo: Int,
    val latitude: Int,
    val longitude: Int,
    val loungesFrom: Int,
    val loungesTo: Int,
    val phone: String,
    val remindOn: String,
    val status: String,
    val taskId: Long,
    val title: String
)

fun FullTaskResponse.toTaskInfo(): TaskInfo {
    return TaskInfo(
        floorsFrom = floorsFrom,
        floorsTo = floorsTo,
        loungesFrom = loungesFrom,
        loungesTo = loungesTo,
        subtasksFrom = 123, // Не приходит с этого запроса
        subtasksTo = 321,   // Такой же
        taskId = taskId,
        title = title,

        //Additional info
        priority = priority ?: "Без приоритета",
        creator = creator,
        description = description ?: "Нет описания",
        marks = marks
    )
}