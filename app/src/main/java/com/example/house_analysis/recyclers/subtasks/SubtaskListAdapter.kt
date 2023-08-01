package com.example.house_analysis.recyclers.subtasks

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.data.model.response.Subtask
import com.example.house_analysis.domain.SubtaskDataHelper
import com.example.house_analysis.recyclers.subtasks.model.view.SubtaskView

class SubtaskListAdapter(private val taskId: Long):
    RecyclerView.Adapter<SubtaskListAdapter.MyViewHolder>() {

    private val dataTransfer = DataTransfer()

    private var subtasks = ArrayList<Subtask>()
    private var checkedItemsId = arrayListOf<Long>()

    init {
        dataTransfer.loadList()
    }

    class MyViewHolder(var subtaskView: SubtaskView): RecyclerView.ViewHolder(subtaskView) {
        fun fillItem(subtask: Subtask) {
            subtaskView.apply {
                number?.text = subtask.number.toString()
//                flag?
//                comment?
//                importance?
//                hash?
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(SubtaskView(parent.context))

    override fun getItemCount() = subtasks.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fillItem(subtasks[position])

        val listener = checkListener(holder.subtaskView, position)
        holder.subtaskView.checkBox?.setOnCheckedChangeListener(listener)
        holder.subtaskView.checkBox1?.setOnCheckedChangeListener(listener)
    }

    private fun checkListener(taskView: SubtaskView, position: Int) =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            taskView.checkBox?.isChecked = isChecked
            taskView.checkBox1?.isChecked = isChecked

            val item = subtasks[position]
            if (isChecked) {
                if (!checkedItemsId.contains(item.subtaskId)) {
                    checkedItemsId.add(item.subtaskId)

                }
            } else checkedItemsId.remove(item.subtaskId)
        }

    private inner class DataTransfer {
        private val dataHelper = SubtaskDataHelper()

        @SuppressLint("NotifyDataSetChanged")
        fun loadList() {
            dataHelper.getTaskWithSubtasks(taskId) {
                subtasks = ArrayList(it.subtasks)
                notifyDataSetChanged()
            }
        }
    }
}