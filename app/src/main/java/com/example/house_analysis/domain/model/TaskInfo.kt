package com.example.house_analysis.domain.model

import com.example.house_analysis.data.model.response.additional.Creator
import com.example.house_analysis.data.model.response.additional.Mark

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