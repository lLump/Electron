package com.example.house_analysis.recyclers.tasks

interface DataTransferInterface {
    fun reloadList()

    fun loadSlideInfo(position: Int)

    fun deleteTasks()
}