package com.example.house_analysis.presentation.ui.profile.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentSubtasksBinding
import com.example.house_analysis.presentation.recyclers.ItemClickSupport
import com.example.house_analysis.presentation.recyclers.subtasks.SubtaskListAdapter
import com.example.house_analysis.presentation.ui.profile.bottom_nav.dialogs.DotDialogListener
import com.example.house_analysis.presentation.ui.profile.bottom_nav.dialogs.DotsAction
import com.example.house_analysis.presentation.ui.profile.bottom_nav.dialogs.TaskDotsDialog

class SubtasksFragment : Fragment(), DotDialogListener {
    private lateinit var binding: FragmentSubtasksBinding
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubtasksBinding.inflate(inflater, container, false)

        var taskId = 0L
        arguments?.let {
            taskId = it.getLong(TASK_ID)
        }
        initRecycler(taskId)

        return binding.root
    }

    private fun initRecycler(taskId: Long) {
        recycler = binding.subtasksRecycler
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = SubtaskListAdapter(taskId)
        setItemRecyclerListener()
    }

    private fun setItemRecyclerListener() {
        ItemClickSupport.addTo(recycler)
            ?.setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView?, position: Int, view: View) {

                }
            })
    }

    fun openSubtaskDotsDialog(dialogId: Int) {
        val dialog = TaskDotsDialog(dialogId)
        dialog.listener = this
        dialog.show(parentFragmentManager, "Tasks")
    }

    override fun onTaskDialogAction(action: DotsAction) {
        when (action) {
            DotsAction.COMMENT -> {}
            DotsAction.STATUS -> openSubtaskDotsDialog(R.layout.dialog_change_status)
            DotsAction.PRIORITY -> openSubtaskDotsDialog(R.layout.dialog_change_priority)
            //Status
            DotsAction.RED -> changeSubtaskStatus("CANCELED")
            DotsAction.ORANGE -> changeSubtaskStatus("IN_WORK")
            DotsAction.GREEN -> changeSubtaskStatus("DONE")
            DotsAction.BLUE -> changeSubtaskStatus("DEFAULT")
            //
            //Priority
            DotsAction.UHARD -> changeSubtaskPriority("URGENT_HARD")
            DotsAction.UEASY -> changeSubtaskPriority("URGENT_EASY")
            DotsAction.HARD -> changeSubtaskPriority("NON_URGENT_HARD")
            DotsAction.EASY -> changeSubtaskPriority("NON_URGENT_EASY")

            //
            else -> {}
        }
    }

    private fun changeSubtaskStatus(status: String) {
        (recycler.adapter as SubtaskListAdapter).dataTransfer.editSubtask(status = status)
    }

    private fun changeSubtaskPriority(priority: String) {
        (recycler.adapter as SubtaskListAdapter).dataTransfer.editSubtask(priority = priority)
    }

    companion object {
        const val TASK_ID = "taskId"

        fun newInstance(taskId: Long): SubtasksFragment {
            val fragment = SubtasksFragment()
            val args = Bundle()
            args.putLong(TASK_ID, taskId)
            fragment.arguments = args
            return fragment
        }
    }
}