package com.example.house_analysis.ui.profile.bottom_nav.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.house_analysis.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskDotDialog: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_bottom_task, container, false)

        return view
    }
}