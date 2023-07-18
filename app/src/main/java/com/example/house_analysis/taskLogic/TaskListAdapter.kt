package com.example.house_analysis.taskLogic

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.TasksResponse

class TaskListAdapter:
    RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {

    val dataTransfer = TaskDataTransfer()

    init {
//        dataHelper.getAllTasks()
        dataTransfer.reloadList()
    }

    class TaskHolder(var taskItemView: TaskView):RecyclerView.ViewHolder(taskItemView){
        @SuppressLint("SetTextI18n")
        fun updateWith(task: TasksResponse){
            taskItemView.apply {
                address?.text = task.title
                apartmentLis?.text = "${task.subtasksFrom}-${task.subtasksTo}"
                floorList?.text = "${task.floorsFrom}-${task.floorsTo}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val itemView = TaskView(parent.context)
        return TaskHolder(itemView)
    }
    override fun getItemCount() = dataTransfer.tasks.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.updateWith(dataTransfer.tasks[position])

        val listener = checkListener(holder.taskItemView, position)
        holder.taskItemView.checkBox?.setOnCheckedChangeListener(listener)
        holder.taskItemView.checkBox1?.setOnCheckedChangeListener(listener)
    }

    private fun checkListener(taskView: TaskView, position: Int) =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            taskView.checkBox?.isChecked = isChecked
            taskView.checkBox1?.isChecked = isChecked

            if (isChecked) {
                if(!dataTransfer.checkedTasksId.contains(dataTransfer.tasks[position].taskId)) {
                    dataTransfer.checkedTasksId.add(dataTransfer.tasks[position].taskId)
                }
            }
            else dataTransfer.checkedTasksId.remove(dataTransfer.tasks[position].taskId)
        }

    inner class TaskDataTransfer {
        private val dataHelper = TaskDataHelper()
        internal var tasks = arrayListOf<TasksResponse>()
        internal var checkedTasksId = arrayListOf<Int>()

        fun deleteChosenTasks() {
            checkedTasksId.forEach {
                dataHelper.deleteTask(it)
                removeTask(it)

            }
            checkedTasksId.clear()
        }

        private fun removeTask(id: Int) {
            val pos = tasks.indexOfFirst { it.taskId == id }
            if (pos != -1) {
                tasks.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun reloadList() {
            dataHelper.getAllTasks {
                tasks = it
                notifyDataSetChanged()
            }
        }
    }
}