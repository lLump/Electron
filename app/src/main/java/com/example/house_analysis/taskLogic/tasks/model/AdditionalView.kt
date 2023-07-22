package com.example.house_analysis.taskLogic.tasks.model

import android.content.Context
import android.widget.LinearLayout
import com.example.house_analysis.R

class AdditionalView(context: Context): LinearLayout(context) {


    init {
        inflate(context, R.layout.list_drop_item, this)

    }
}