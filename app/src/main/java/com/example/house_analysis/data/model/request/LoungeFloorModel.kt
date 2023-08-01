package com.example.house_analysis.data.model.request

data class LoungeFloorModel(
    val floorNumber: Int,
    val loungeNumber: Int,
    val subtaskIds: List<Long>
)
