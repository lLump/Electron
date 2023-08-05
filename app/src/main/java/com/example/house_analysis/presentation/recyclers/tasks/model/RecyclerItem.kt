package com.example.house_analysis.presentation.recyclers.tasks.model

sealed class RecyclerItem {
    data class TaskItem(val task: TaskInfo) : RecyclerItem()
    data class SlideItem(val slide: TaskInfo) : RecyclerItem()
}