package com.example.lembretes.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.lembretes.databinding.ActivityMainBinding
import com.example.lembretes.datasource.TaskDataSource
import com.example.lembretes.model.Task
import com.example.lembretes.viewModel.TaskViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    private lateinit var viewModel : TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskViewModel.TaskViewModelFactory(this.application)
        ).get(TaskViewModel::class.java)

        viewModel.allTasks.observe(this) { list ->

            updateList(list)
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }

        adapter.listenerCancel = {
            TaskDataSource.deleteTask(it)
//            updateList(viewModel.allTasks.value)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList(viewModel.allTasks.value)
    }

    private fun updateList (list: List<Task>?) {

        binding.includeEmpty.emptyState.visibility = if (list?.isEmpty() == true) View.VISIBLE
        else View.GONE

        adapter.submitList(list)
    }

//    private fun updateList () {
//        viewModel.readAllData.observe(this, Observer {list->
//            binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
//            else View.GONE
//
//            binding.rvTasks.visibility = if (list.isNotEmpty()) View.VISIBLE
//            else View.GONE
//
//            adapter.submitList(list)
//        })
//    }

    companion object {
        private const val CREATE_NEW_TASK = 100
    }

}
