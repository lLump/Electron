package com.example.house_analysis.data.model.response

data class TasksResponse(
    //Additional info when 1 task requested
    val creator: Creator,
    val performers: List<Performer>,
    val marks: List<Mark>,
    val description: String, //Comment
    val priority: String,

    //All user tasks response
    val taskId: Long,
    val title: String,
    val floorsFrom: Int,
    val floorsTo: Int,
    val loungesFrom: Int,
    val loungesTo: Int,
    val subtasksFrom: Int,
    val subtasksTo: Int
)

data class Creator(
    val email: String,
    val fullname: String,
    val phone: String
)

data class Performer(
    val email: String,
    val fullname: String,
    val phone: String
)

data class Mark(
    val context: String,
    val id: Long
)
