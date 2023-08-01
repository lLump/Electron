package com.example.house_analysis.recyclers.subtasks.model.view

import android.content.Context
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.house_analysis.R

class SubtaskView(context: Context): LinearLayout(context) {
    var checkBox: CheckBox? = null
    var checkBox1: CheckBox? = null
    var number: TextView? = null
    var flag: ImageView? = null
    var comment: ImageView? = null
    var importance: TextView? = null
    var hash: ImageView? = null

    init {
        inflate(context, R.layout.list_subtask_item, this)

        checkBox = findViewById(R.id.sub_check1)
        checkBox1 = findViewById(R.id.sub_check2)
        number = findViewById(R.id.subtask_number)
        flag = findViewById(R.id.sub_flag)
        comment = findViewById(R.id.sub_comment)
        importance = findViewById(R.id.sub_importance)
        hash = findViewById(R.id.sub_hash)
    }
}