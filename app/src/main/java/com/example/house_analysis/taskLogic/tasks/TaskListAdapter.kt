package com.example.house_analysis.taskLogic.tasks

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.taskLogic.tasks.model.AdditionalView
import com.example.house_analysis.taskLogic.tasks.model.view.TaskList
import com.example.house_analysis.taskLogic.tasks.model.view.TaskView

class TaskListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataTransfer = TaskDataTransfer()

    internal var tasks = arrayListOf<TasksResponse>()
    private var uiItems = arrayListOf<TaskList>()
    private var checkedTasksId = arrayListOf<Int>()

    init {
        dataTransfer.reloadList()
    }

    class TaskHolder(var taskItemView: TaskView) : RecyclerView.ViewHolder(taskItemView) {
        @SuppressLint("SetTextI18n")
        fun updateWith(task: TasksResponse) {
            taskItemView.apply {
                address?.text = task.title
                apartmentLis?.text = "${task.subtasksFrom}-${task.subtasksTo}"
                floorList?.text = "${task.floorsFrom}-${task.floorsTo}"
            }
        }
    }


    class AdditionalInfoHolder(var additionalItemView: AdditionalView) :
        RecyclerView.ViewHolder(additionalItemView) {
        fun updateInfo(task: TasksResponse) {
            additionalItemView.apply {
                // TODO
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            VIEW_ITEM -> viewHolder = TaskHolder(TaskView(parent.context))
            VIEW_ADDITIONAL -> viewHolder = AdditionalInfoHolder(AdditionalView(parent.context))
        }

        return viewHolder!!
    }

    private fun getItemPosById(id: Int) = tasks.indexOfFirst { it.taskId == id }

    override fun getItemCount() = uiItems.size

    override fun getItemViewType(position: Int): Int {
        return when (uiItems[position]) {
            is TaskList.TaskItem -> VIEW_ITEM
            is TaskList.AdditionalItem -> VIEW_ADDITIONAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_ITEM -> {
                holder as TaskHolder
                holder.updateWith(tasks[position])

                val listener = checkListener(holder.taskItemView, position)
                holder.taskItemView.checkBox?.setOnCheckedChangeListener(listener)
                holder.taskItemView.checkBox1?.setOnCheckedChangeListener(listener)
            }

            VIEW_ADDITIONAL -> {
                (holder as AdditionalInfoHolder).updateInfo(tasks[position])
            }
        }

    }

    private fun checkListener(taskView: TaskView, position: Int) =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            taskView.checkBox?.isChecked = isChecked
            taskView.checkBox1?.isChecked = isChecked

            if (isChecked) {
                if (!checkedTasksId.contains(tasks[position].taskId)) {
                    checkedTasksId.add(tasks[position].taskId)
                }
            } else checkedTasksId.remove(tasks[position].taskId)
        }

    inner class TaskDataTransfer {
        private val dataHelper = TaskDataHelper()

        fun getAdditionalInfo(position: Int) {
            dataHelper.getTask(tasks[position].taskId) {
                tasks.add(it)
                uiItems.add(TaskList.AdditionalItem(it))
                notifyDataSetChanged() //FIXME
            }
        }

        fun deleteChosenTasks() {
            checkedTasksId.forEach {
                dataHelper.deleteTask(it)
                removeTask(it)
            }
            checkedTasksId.clear()
        }

        private fun removeTask(id: Int) {
            val pos = getItemPosById(id)
            if (pos != -1) {
                tasks.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun reloadList() {
            dataHelper.getAllTasks {
                tasks = it
//                tasks.addAll(TaskList.TaskItem(it))
                tasks.forEach {
                    uiItems.add(TaskList.TaskItem(it))
                }
                notifyDataSetChanged()
            }

        }
    }

    companion object {
        private const val VIEW_ITEM = 0
        private const val VIEW_ADDITIONAL = 1
    }
}