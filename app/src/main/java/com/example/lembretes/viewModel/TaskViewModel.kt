package com.example.lembretes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lembretes.datasource.db.AppDatabase
import com.example.lembretes.datasource.repository.TaskDbDataSource
import com.example.lembretes.model.Task
import com.example.lembretes.ui.RegistrationViewParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Task>>
    private val repository: TaskDbDataSource

    init {
        val taskDao = AppDatabase.getDataBase(application).taskDao()
        repository = TaskDbDataSource(taskDao)
        readAllData = repository.getTasks()
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

    fun getTasks(): LiveData<List<Task>> {
        return repository.getTasks()
    }

    suspend fun getTaskList(): LiveData<List<Task>> {
        val result = MutableLiveData<List<Task>>()
        viewModelScope.launch(Dispatchers.IO) {
            val returnedrepo = repository.getTasksList()
            println("AGM 2 getTaskList ViewModel = $returnedrepo")
            result.postValue(returnedrepo)

        }
        println("AGM 3 result.value no ViewModel = $result.value")
        return result
    }

    fun getTasksCoroutines(){
        CoroutineScope(Dispatchers.Main).launch {
            val tasks = withContext(Dispatchers.Default) {
                repository.getTasksCoroutines()
            }

            readAllData.value = tasks

        }
    }

}