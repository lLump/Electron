package com.example.house_analysis.taskLogic.tasks

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.taskLogic.tasks.model.view.AdditionalView
import com.example.house_analysis.taskLogic.tasks.model.RecyclerItem
import com.example.house_analysis.taskLogic.tasks.model.view.TaskView

class TaskListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataTransfer = TaskDataTransfer()

    private var uiItems = arrayListOf<RecyclerItem>()
    private var checkedTasksId = arrayListOf<Long>()

    init {
        dataTransfer.reloadList()
    }

    class TaskHolder(var taskItemView: TaskView) : RecyclerView.ViewHolder(taskItemView) {
        @SuppressLint("SetTextI18n")
        fun fillTaskItem(task: TasksResponse) {
            taskItemView.apply {
                address?.text = task.title
                apartmentLis?.text = "${task.subtasksFrom}-${task.subtasksTo}"
                floorList?.text = "${task.floorsFrom}-${task.floorsTo}"
            }
        }
    }


    inner class AdditionalInfoHolder(var additionalItemView: AdditionalView) :
        RecyclerView.ViewHolder(additionalItemView) {
        @SuppressLint("SetTextI18n")
        fun fillAdditionalView(task: TasksResponse) {
            val mainItemPos = getItemPosById(task.taskId)
            val mainItem = uiItems.getItem(mainItemPos)
            additionalItemView.apply {
                //flag (priority) TODO
                apartments?.text =
                    "${mainItem.subtasksFrom} - ${mainItem.subtasksTo}"
                floors?.text = "${task.floorsFrom} - ${task.floorsTo}"
                entrance?.text = "${task.loungesFrom} - ${task.loungesTo}"
                fullName?.text = task.creator.fullname
                phone?.text = task.creator.phone
                comment?.text = task.description
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

    private fun getItemPosById(id: Long) =
        uiItems.indexOfFirst {
            when (it) {
                is RecyclerItem.TaskItem -> it.task.taskId == id
                is RecyclerItem.AdditionalItem -> it.task.taskId == id
            }
        }


    override fun getItemCount() = uiItems.size

    override fun getItemViewType(position: Int) =
        when (uiItems[position]) {
            is RecyclerItem.TaskItem -> VIEW_ITEM
            is RecyclerItem.AdditionalItem -> VIEW_ADDITIONAL
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_ITEM -> {
                holder as TaskHolder
                holder.fillTaskItem(uiItems.getItem(position))

                val listener = checkListener(holder.taskItemView, position)
                holder.taskItemView.checkBox?.setOnCheckedChangeListener(listener)
                holder.taskItemView.checkBox1?.setOnCheckedChangeListener(listener)
            }
            VIEW_ADDITIONAL -> {
                (holder as AdditionalInfoHolder).fillAdditionalView(uiItems.getItem(position))
            }
        }
    }

    private fun checkListener(taskView: TaskView, position: Int) =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            taskView.checkBox?.isChecked = isChecked
            taskView.checkBox1?.isChecked = isChecked

            val task = uiItems.getItem(position)
            if (isChecked) {
                if (!checkedTasksId.contains(task.taskId)) {
                    checkedTasksId.add(task.taskId)

                }
            } else checkedTasksId.remove(task.taskId)
        }

    private fun ArrayList<RecyclerItem>.getItem(position: Int): TasksResponse {
        return when (val item = this[position]) {
            is RecyclerItem.TaskItem -> item.task
            is RecyclerItem.AdditionalItem -> item.task
        }
    }

    inner class TaskDataTransfer {
        private val dataHelper = TaskDataHelper()
        private var taskIdsWithAdditional = arrayListOf<Long>()

        fun getAdditionalInfo(position: Int) {
            val id = uiItems.getItem(position).taskId
            if (taskIdsWithAdditional.contains(id)) {   // Remove view if displayed
                uiItems.removeAt(position + 1)
                notifyItemRemoved(position + 1)
                taskIdsWithAdditional.remove(id)
            } else {                                    // Get and set info
                taskIdsWithAdditional.add(id)
                getAndSetAdditional(id, position)
            }
        }

        private fun getAndSetAdditional(id: Long, mainItemPosition: Int) {
            dataHelper.getTask(id) {
                uiItems.add(mainItemPosition + 1, RecyclerItem.AdditionalItem(it))
                notifyItemInserted(mainItemPosition + 1)
            }
        }

        fun deleteChosenTasks() {
            checkedTasksId.forEach {
                dataHelper.deleteTask(it)
                removeTask(it)
            }
            checkedTasksId.clear()
        }

        private fun removeTask(id: Long) {
            val pos = getItemPosById(id)
            uiItems.removeAt(pos)
            notifyItemRemoved(pos)
            removeAdditionalViewIfExist(id)
        }

        private fun removeAdditionalViewIfExist(id: Long) {
            val pos = getItemPosById(id)
            if (pos != -1) {
                if (uiItems[pos] is RecyclerItem.AdditionalItem) {
                    uiItems.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun reloadList() {
            val temp = arrayListOf<RecyclerItem>()
            dataHelper.getAllTasks { tasks ->
                tasks.forEach { item ->
                    temp.add(RecyclerItem.TaskItem(item))
                    if(taskIdsWithAdditional.contains(item.taskId)) {
                        getAndSetAdditional(item.taskId, getItemPosById(item.taskId))
                    }
                }
                uiItems = temp
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val VIEW_ITEM = 0
        private const val VIEW_ADDITIONAL = 1
    }
}