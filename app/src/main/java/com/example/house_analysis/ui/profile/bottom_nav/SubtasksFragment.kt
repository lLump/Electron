package com.example.house_analysis.ui.profile.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.databinding.FragmentSubtasksBinding
import com.example.house_analysis.recyclers.ItemClickSupport
import com.example.house_analysis.recyclers.subtasks.SubtaskListAdapter

class SubtasksFragment : Fragment() {
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