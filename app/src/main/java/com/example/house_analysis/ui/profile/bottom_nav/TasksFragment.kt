package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentTasksBinding
import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.recyclers.ItemClickSupport
import com.example.house_analysis.recyclers.tasks.TaskListAdapter
import com.example.house_analysis.ui.profile.bottom_nav.dialogs.DotsAction
import com.example.house_analysis.ui.profile.bottom_nav.dialogs.DotDialogListener
import com.example.house_analysis.ui.profile.bottom_nav.dialogs.TaskDotsDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class TasksFragment : Fragment(), DotDialogListener {
    private lateinit var binding : FragmentTasksBinding
    private lateinit var recyclerView: RecyclerView
    inner class RequestHelper {
        private val networkRepository = RequestRepository
        fun createTask(address: String, from: Int, to: Int) {
            lifecycleScope.launch {
                networkRepository.createTask(TaskRequestModel(address, from, to))
                (recyclerView.adapter as TaskListAdapter).dataTransfer.reloadList()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createTask.setOnClickListener {
            showTaskCreatorDialog()
        }

    }

    private fun initRecycler() {
        recyclerView = binding.rvTasks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TaskListAdapter()
        setItemRecyclerListeners()
    }

    private fun setItemRecyclerListeners() {
        ItemClickSupport.addTo(recyclerView)
            ?.setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
                    if (isMainView(position)) {
                        //TODO
                    }
                }
            })
            ?.setOnItemLongClickListener(object: ItemClickSupport.OnItemLongClickListener {
                override fun onItemLongClicked(recyclerView: RecyclerView?, position: Int, v: View?): Boolean {
                    if (isMainView(position)) {
                        (recyclerView?.adapter as TaskListAdapter).dataTransfer.getAdditionalInfo(position)
                    }
                    return true
                }
            })

    }

    private fun isMainView(pos: Int): Boolean { //Check for type of recyclerItem
        val viewType = recyclerView.adapter?.getItemViewType(pos)
        return viewType == 0
    }

//    private fun ifNoTasks() {
//        if ((recyclerView.adapter as TaskListAdapter).tasks.isEmpty()) { //FIXME
//            binding.ifTasksNotExists.visibility = View.VISIBLE
//            binding.ifTasksExists.visibility = View.GONE
//        }
//        else {
//            binding.ifTasksNotExists.visibility = View.GONE
//            binding.ifTasksExists.visibility = View.VISIBLE
//        }
//    }

    private fun showTaskCreatorDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_create_new_task)

        // Set dialog width and height
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT // Set height to wrap content

        dialog.findViewById<ImageView>(R.id.closeDialog).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.createTask).setOnClickListener {
            val address = dialog.findViewById<TextInputEditText>(R.id.text_address).text.toString()
            val from = dialog.findViewById<TextInputEditText>(R.id.text_from).text.toString().toInt()
            val to = dialog.findViewById<TextInputEditText>(R.id.text_to).text.toString().toInt()

            RequestHelper().createTask(address, from, to)

            dialog.dismiss()
        }

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun openTaskDotsDialog() {
        val dialog = TaskDotsDialog(R.layout.dialog_bottom_task)
        dialog.listener = this
        dialog.show(parentFragmentManager, "Tasks")
    }

    override fun onTaskDialogAction(action: DotsAction) {
        when (action) {
            DotsAction.MANAGER -> nothing() //TODO
            DotsAction.PHONE -> nothing()
            DotsAction.COMMENT -> nothing()
            DotsAction.LABEL -> nothing()
            DotsAction.EDIT -> nothing()
            DotsAction.DELETE -> (recyclerView.adapter as TaskListAdapter).dataTransfer.deleteChosenTasks()
        }
    }

    private fun nothing() {}
}