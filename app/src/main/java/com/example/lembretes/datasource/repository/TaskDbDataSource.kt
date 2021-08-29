package com.example.lembretes.datasource.repository

import androidx.lifecycle.LiveData
import com.example.lembretes.datasource.db.dao.TaskDao
import com.example.lembretes.model.Task
import com.example.lembretes.model.TaskDB
import com.example.lembretes.model.toTask
import com.example.lembretes.model.toTaskDB
import com.example.lembretes.ui.RegistrationViewParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskDbDataSource(
    private val taskDao: TaskDao
): TaskRepository {

    lateinit var readAllData: LiveData<List<Task>>

    override suspend fun createTaks(registrationViewParams: RegistrationViewParams) {
        val task = registrationViewParams.toTask()
        taskDao.save(task)
    }

    override fun getTask(id: Int): TaskDB {
        return taskDao.getTask(id).toTaskDB()
    }

    override fun getTasks(): LiveData<List<Task>> {
        return taskDao.getTasks()
    }

    override suspend fun getTasksList(): List<Task> {
        val lala =  taskDao.getTasksList()
        println("AGM 1 getTaskList repository = $lala")
        return lala
    }

    suspend fun getTasksCoroutines(): List<Task> {
        return withContext(Dispatchers.Default) {
            taskDao.getTasksList()
        }
    }
}