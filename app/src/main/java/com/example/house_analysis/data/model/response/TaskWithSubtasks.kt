package com.example.house_analysis.data.model.response

data class TaskWithSubtasks(
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