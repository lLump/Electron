package com.example.house_analysis.presentation.recyclers.subtasks

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.data.model.response.subtasks.Subtask
import com.example.house_analysis.data.model.response.subtasks.toSubtaskDto
import com.example.house_analysis.domain.usecase.subtask.SubtasksInterceptor
import com.example.house_analysis.presentation.recyclers.subtasks.model.view.SubtaskView

class SubtaskListAdapter(private val taskId: Long) :
    RecyclerView.Adapter<SubtaskListAdapter.MyViewHolder>() {

    val dataTransfer = DataTransfer()

    private var subtasks = ArrayList<Subtask>()
    private var checkedItemsId = arrayListOf<Long>()

    init {
        dataTransfer.loadList()
    }

    class MyViewHolder(var subtaskView: SubtaskView) : RecyclerView.ViewHolder(subtaskView) {
        fun fillItem(subtask: Subtask) {
            subtaskView.apply {
                number?.text = subtask.number.toString()
                flag?.setImageDrawable(resources.getDrawable(getFlagType(subtask.status)))
                comment?.setImageDrawable(resources.getDrawable(getCommentImage(subtask.isDescribed)))
                priority?.text = getPriority(subtask.priority?: "null", priority)
//                hash?
            }
        }

        private fun getPriority(priority: String, view: TextView?): String {
            return when (priority){
                "URGENT_HARD" -> {
                    view?.setTextColor(Color.parseColor("#E03F32"))
                    "СиС"
                }
                "URGENT_EASY" -> {
                    view?.setTextColor(Color.parseColor("#F27E00"))
                    "СиЛ"
                }
                "NON_URGENT_HARD" -> {
                    view?.setTextColor(Color.parseColor("#00A759"))
                    "неСиС"
                }
                "NON_URGENT_EASY" -> {
                    view?.setTextColor(Color.parseColor("#00A3D4"))
                    "неСиЛ"
                }
                else -> ""
            }
        }

        private fun getCommentImage(described: Boolean) =
            if (described) R.drawable.icon_comment_blue
            else R.drawable.icon_comment

        private fun getFlagType(status: String): Int {
            return when (status) {
                "CANCELED" -> R.drawable.flag_red
                "IN_WORK" -> R.drawable.flag_orange
                "DONE" -> R.drawable.flag_green
                "DEFAULT" -> R.drawable.flag_blue
                else -> R.drawable.flag_empty
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SubtaskView(parent.context))

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

    private fun getItemById(id: Long): Subtask {
        val pos = subtasks.indexOfFirst {
            it.subtaskId == id
        }
        return subtasks[pos]
    }

    inner class DataTransfer {
        private val repository = SubtasksInterceptor()

        @SuppressLint("NotifyDataSetChanged")
        fun loadList() {
            subtasks.clear()
            checkedItemsId.clear()
            repository.getTaskWithSubtasks(taskId) {
                subtasks = ArrayList(it.subtasks)
                notifyDataSetChanged()
            }
        }

        fun editSubtask(status: String = "NEW", priority: String? = null, description: String? = null) {
            val subtasks = getCheckedSubtasks()
            subtasks.forEach {
                val send = it.toSubtaskDto()
                send.apply {
                    this.description = description
                    this.status = status
//                    this.floor = floor
//                    this.lounge = lounge
                    this.priority = priority
                }
                repository.editSubtask(it.subtaskId, send) {
                    loadList()
                }
            }
        }

        private fun getCheckedSubtasks(): ArrayList<Subtask> {
            val subtasks = arrayListOf<Subtask>()
            checkedItemsId.forEach {
                subtasks.add(getItemById(it))
            }
            return subtasks
        }
    }
}