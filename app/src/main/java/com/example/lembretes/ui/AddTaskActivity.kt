package com.example.lembretes.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lembretes.databinding.ActivityAddTaskBinding
import com.example.lembretes.databinding.ActivityMainBinding
import com.example.lembretes.datasource.TaskDataSource
import com.example.lembretes.extensions.format
import com.example.lembretes.extensions.text
import com.example.lembretes.model.Task
import com.example.lembretes.viewModel.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?. let {
                binding.tilTitle.text = it.title
                binding.tilDescricao.text = it.description
                binding.tilData.text = it.date
                binding.tilHora.text = it.hour

            }
        }

        insertListeners()

    }

    private fun insertListeners() {
        binding.tilData.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilData.text = Date(it + offset).format()

            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")

        }

        binding.tilHora.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.tilHora.text = "$hour:$minute"

            }

            timePicker.show(supportFragmentManager, null)

        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnCriar.setOnClickListener {
        val task = Task(
            title = binding.tilTitle.text,
            description = binding.tilDescricao.text,
            date = binding.tilData.text,
            hour = binding.tilHora.text,
            id = intent.getIntExtra(TASK_ID, 0)

        )
            TaskDataSource.insertTask(task)
            mTaskViewModel.addTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }
        
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}