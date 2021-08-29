package com.example.lembretes.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.lembretes.datasource.db.AppDatabase
import com.example.lembretes.datasource.repository.TaskDbDataSource
import com.example.lembretes.model.Task
import com.example.lembretes.ui.RegistrationViewParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TaskDbDataSource
    val allTasks = MutableLiveData<List<Task>>()

    init {
        val taskDao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskDbDataSource(taskDao)

        viewModelScope.launch(Dispatchers.IO) {
            allTasks.postValue(repository.getAllTasksList())
        }


    }

    fun addTask(task: Task) {

        viewModelScope.launch(Dispatchers.IO) {
            var registrationViewParams = RegistrationViewParams(
                title = task.title,
                description = task.description,
                date = task.date,
                hour = task.hour
            )
            repository.createTaks(registrationViewParams)
        }
    }

//    fun getAllTasks() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val tasks = repository.getAllTasks().value
//            allTasks.postValue(tasks)
//        }
//
//    }

    class TaskViewModelFactory constructor(private val application: Application):
            ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                        TaskViewModel(this.application) as T

                    }else {
                        throw IllegalArgumentException("ViewModel not found")
                    }
                }
            }
}