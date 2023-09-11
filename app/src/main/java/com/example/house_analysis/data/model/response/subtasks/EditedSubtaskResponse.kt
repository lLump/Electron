package com.example.house_analysis.data.model.response.subtasks

import com.example.house_analysis.data.model.response.additional.Creator
import com.example.house_analysis.data.model.response.additional.Mark

data class EditedSubtaskResponse(
    val contact: String,
    val createdOn: String,
    val creator: Creator,
    val description: String,
    val floor: Int,
    val id: Int,
    val lounge: Int,
    val marks: List<Mark>,
    val number: Int,
    val phone: String,
    val priority: String,
    val remindOn: String,
    val status: String,
    val taskId: Int
)