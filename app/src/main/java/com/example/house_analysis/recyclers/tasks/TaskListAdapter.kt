package com.example.house_analysis.recyclers.tasks

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.data.model.response.TasksResponse
import com.example.house_analysis.domain.TaskDataHelper
import com.example.house_analysis.recyclers.tasks.model.view.SlideView
import com.example.house_analysis.recyclers.tasks.model.RecyclerItem
import com.example.house_analysis.recyclers.tasks.model.view.TaskView

class TaskListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataTransfer = DataTransfer()

    private var uiItems = arrayListOf<RecyclerItem>()
    private var taskIdsWithSlide = arrayListOf<Long>()
    private var checkedTasksId = arrayListOf<Long>()

    init {
        dataTransfer.reloadList()
    }

    private class TaskHolder(var taskItemView: TaskView) : RecyclerView.ViewHolder(taskItemView) {
        @SuppressLint("SetTextI18n")
        fun fillTaskItem(task: TasksResponse) {
            taskItemView.apply {
                address?.text = task.title
                apartmentLis?.text = "${task.subtasksFrom}-${task.subtasksTo}"
                floorList?.text = "${task.floorsFrom}-${task.floorsTo}"
            }
        }
    }

    private inner class SlideInfoHolder(var slideItemView: SlideView) :
        RecyclerView.ViewHolder(slideItemView) {
        @SuppressLint("SetTextI18n")
        fun fillSlideView(task: TasksResponse) {
            val mainItemPos = getItemPosById(task.taskId)
            val mainItem = uiItems.getItem(mainItemPos)
            slideItemView.apply {
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
            VIEW_SLIDE -> viewHolder = SlideInfoHolder(SlideView(parent.context))
        }
        return viewHolder!!
    }

    override fun getItemCount() = uiItems.size

    override fun getItemViewType(position: Int) =
        when (uiItems[position]) {
            is RecyclerItem.TaskItem -> VIEW_ITEM
            is RecyclerItem.SlideItem -> VIEW_SLIDE
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
            VIEW_SLIDE -> {
                (holder as SlideInfoHolder).fillSlideView(uiItems.getItem(position))
            }
        }
    }

    private fun getItemPosById(id: Long) =
        uiItems.indexOfFirst {
            when (it) {
                is RecyclerItem.TaskItem -> it.task.taskId == id
                is RecyclerItem.SlideItem -> it.task.taskId == id
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
            is RecyclerItem.SlideItem -> item.task
        }
    }

    fun getItemIdByPos(pos: Int) = uiItems.getItem(pos).taskId

    fun getInterface() = dataTransfer.myInterface

    private inner class DataTransfer: DataTransferInterface {
        var myInterface: DataTransferInterface = this
        private val dataHelper = TaskDataHelper()

        @SuppressLint("NotifyDataSetChanged")
        override fun reloadList() {
            val temp = arrayListOf<RecyclerItem>()
//            uiItems.clear()
            dataHelper.getAllTasks { tasks ->
                tasks.forEach { item ->
                    temp.add(RecyclerItem.TaskItem(item))
                    if(taskIdsWithSlide.contains(item.taskId)) {
                        getAndSetSlideInfo(item.taskId, getItemPosById(item.taskId))
                    }
                }
                uiItems = temp
                notifyDataSetChanged()
            }
        }

        override fun loadSlideInfo(position: Int) {
            val id = uiItems.getItem(position).taskId
            if (taskIdsWithSlide.contains(id)) {        // Remove view if displayed
                uiItems.removeAt(position + 1)
                notifyItemRemoved(position + 1)
                taskIdsWithSlide.remove(id)
            } else {                                    // Get and set info
                taskIdsWithSlide.add(id)
                getAndSetSlideInfo(id, position)
            }
        }

        override fun deleteTasks() {
            checkedTasksId.forEach {
                dataHelper.deleteTask(it)
                removeTask(it)
            }
            checkedTasksId.clear()
        }

        private fun getAndSetSlideInfo(id: Long, mainItemPosition: Int) {
            dataHelper.getTask(id) {
                uiItems.add(mainItemPosition + 1, RecyclerItem.SlideItem(it))
                notifyItemInserted(mainItemPosition + 1)
            }
        }

        private fun removeTask(id: Long) {
            val pos = getItemPosById(id)
            uiItems.removeAt(pos)
            notifyItemRemoved(pos)
            removeSlideViewIfExist(id)
        }

        private fun removeSlideViewIfExist(id: Long) {
            val pos = getItemPosById(id)
            if (pos != -1) {
                if (uiItems[pos] is RecyclerItem.SlideItem) {
                    uiItems.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }

    }

    companion object {
        private const val VIEW_ITEM = 0
        private const val VIEW_SLIDE = 1
    }
}