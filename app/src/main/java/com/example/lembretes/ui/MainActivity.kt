package com.example.lembretes.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.lembretes.databinding.ActivityMainBinding
import com.example.lembretes.datasource.TaskDataSource
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

        updateList()

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
            updateList()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList () {
        val list = viewModel.allTasks.value
            binding.includeEmpty.emptyState.visibility = if (list?.isEmpty() == true) View.VISIBLE
        else View.GONE

        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 100
    }

}
