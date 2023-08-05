package com.example.house_analysis.presentation.recyclers.tasks.model

import com.example.house_analysis.data.model.response.Task
import com.example.house_analysis.data.model.response.full_task.Creator
import com.example.house_analysis.data.model.response.full_task.Mark

data class TaskInfo (
    //Main Info
    val floorsFrom: Int,
    val floorsTo: Int,
    val loungesFrom: Int,
    val loungesTo: Int,
    val subtasksFrom: Int,
    val subtasksTo: Int,
    val taskId: Long,
    val title: String,

    //Additional info
    val priority: String,
    val creator: Creator,
    val description: String,
    val marks: List<Mark>
)