package com.example.house_analysis.data.model.response.subtasks

import com.example.house_analysis.data.model.request.SubtaskDto

data class TaskWithSubtasksResponse(
    val taskId: Long,
    val title: String,
    val totalSubtasks: Int,
    val subtasks: List<Subtask>
)

data class Subtask(
    val number: Int,
    val subtaskId: Long,
    val priority: String?,
    val status: String,
    val floor: Int,
    val lounge: Int,
    val isDescribed: Boolean,
    val isMarked: Boolean
)

fun Subtask.toSubtaskDto(): SubtaskDto {
    return SubtaskDto(
        description = null,
//        floor = floor,
//        lounge = lounge,
        priority = priority,
        status = status,
//        contact = "null",
//        remindOn = "null",
//        phone = "null",
//        markIds = arrayListOf()
    )
}