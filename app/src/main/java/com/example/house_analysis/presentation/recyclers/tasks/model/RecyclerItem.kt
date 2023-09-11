package com.example.house_analysis.presentation.recyclers.tasks.model

import com.example.house_analysis.domain.model.TaskInfo

sealed class RecyclerItem {
    data class TaskItem(val task: TaskInfo) : RecyclerItem()
    data class SlideItem(val slide: TaskInfo) : RecyclerItem()
}