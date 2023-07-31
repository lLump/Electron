package com.example.house_analysis.recyclers.tasks.model.view

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.house_analysis.R

class SlideView(context: Context): LinearLayout(context) {
    var flag: AppCompatImageButton? = null
    var apartments: TextView? = null
    var floors: TextView? = null
    var entrance: TextView? = null // Подъезд
    var fullName: TextView? = null
    var phone: TextView? = null
    var comment: TextView? = null

    init {
        inflate(context, R.layout.list_slide_item, this)

        flag = findViewById(R.id.slide_flag)
        apartments = findViewById(R.id.slide_apartments)
        floors = findViewById(R.id.slide_floors)
        entrance = findViewById(R.id.slide_entrance)
        fullName = findViewById(R.id.slide_fullName)
        phone = findViewById(R.id.slide_phone)
        comment = findViewById(R.id.slide_comment)
    }
}