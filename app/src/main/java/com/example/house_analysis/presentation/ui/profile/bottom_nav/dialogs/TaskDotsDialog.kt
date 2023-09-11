package com.example.house_analysis.presentation.ui.profile.bottom_nav.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.house_analysis.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskDotsDialog(private val dialogId: Int) : BottomSheetDialogFragment() {

    var listener: DotDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(dialogId, container, false)

        // Delete
        view.findViewById<LinearLayout>(R.id.delete_task_layout)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.DELETE)
            dialog?.dismiss()
        }

        // Cancel
        view.findViewById<Button>(R.id.cancel_task_dialog)?.setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }
}
