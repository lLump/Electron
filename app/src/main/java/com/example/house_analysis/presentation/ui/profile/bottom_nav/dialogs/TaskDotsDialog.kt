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

        when (dialogId) {
            R.layout.dialog_bottom_task -> initTaskViews(view)
            R.layout.dialog_bottom_subtask -> initSubtaskViews(view)
            R.layout.dialog_change_status -> initStatusViews(view)
            R.layout.dialog_change_priority -> initPriorityViews(view)
        }

        return view
    }

    private fun initPriorityViews(v: View) {
        v.findViewById<LinearLayout>(R.id.urgent_hard_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.UHARD)
            dialog?.dismiss()
        }
        v.findViewById<LinearLayout>(R.id.urgent_easy_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.UEASY)
            dialog?.dismiss()
        }
        v.findViewById<LinearLayout>(R.id.non_urgent_hard_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.HARD)
            dialog?.dismiss()
        }
        v.findViewById<LinearLayout>(R.id.non_urgent_easy_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.EASY)
            dialog?.dismiss()
        }
    }

    private fun initStatusViews(v: View) {
        // RED
        v.findViewById<LinearLayout>(R.id.red_flag_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.RED)
            dialog?.dismiss()
        }

        // ORANGE
        v.findViewById<LinearLayout>(R.id.orange_flag_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.ORANGE)
            dialog?.dismiss()
        }

        // GREEN
        v.findViewById<LinearLayout>(R.id.green_flag_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.GREEN)
            dialog?.dismiss()
        }

        // BLUE
        v.findViewById<LinearLayout>(R.id.blue_flag_dialog)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.BLUE)
            dialog?.dismiss()
        }
    }

    private fun initTaskViews(v: View) {
        // Delete
        v.findViewById<LinearLayout>(R.id.delete_task_layout)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.DELETE)
            dialog?.dismiss()
        }

        // Cancel
        v.findViewById<Button>(R.id.cancel_task_dialog)?.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initSubtaskViews(v: View) {
        // Cancel
        v.findViewById<Button>(R.id.cancel_subtask_dialog)?.setOnClickListener {
            dialog?.dismiss()
        }

        // Change Priority
        v.findViewById<LinearLayout>(R.id.change_priority_dialog_sub)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.PRIORITY)
            dialog?.dismiss()
        }

        // Change Status
        v.findViewById<LinearLayout>(R.id.change_status_dialog_sub)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.STATUS)
            dialog?.dismiss()
        }

        // Add/change Comment
        v.findViewById<LinearLayout>(R.id.add_comment_dialog_sub)?.setOnClickListener {
            listener?.onTaskDialogAction(DotsAction.COMMENT)
            dialog?.dismiss()
        }
    }
}
