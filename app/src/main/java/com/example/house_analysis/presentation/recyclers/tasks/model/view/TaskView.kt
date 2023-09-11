package com.example.house_analysis.presentation.recyclers.tasks.model.view

import android.content.Context
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.house_analysis.R

class TaskView(context: Context): LinearLayout(context) {
    var checkBox: CheckBox? = null
    var checkBox1: CheckBox? = null
    var address: TextView? = null
    var apartmentLis: TextView? = null
    var floorList: TextView? = null

    init {
        inflate(context, R.layout.list_task_item, this)

        checkBox = findViewById(R.id.sub_check1)
        checkBox1 = findViewById(R.id.isChecked1)
        address = findViewById(R.id.subtask_number)
        apartmentLis = findViewById(R.id.apartmentList)
        floorList = findViewById(R.id.floorList)
    }
}