package com.example.lembretes.datasource.repository

import androidx.lifecycle.LiveData
import com.example.lembretes.datasource.db.dao.TaskDao
import com.example.lembretes.model.Task
import com.example.lembretes.model.TaskDB
import com.example.lembretes.model.toTask
import com.example.lembretes.model.toTaskDB
import com.example.lembretes.ui.RegistrationViewParams

class TaskDbDataSource(
    private val taskDao: TaskDao
): TaskRepository {
    override fun createTaks(registrationViewParams: RegistrationViewParams) {
        val task = registrationViewParams.toTask()
        taskDao.save(task)
        println("Salvou?")
    }

    override fun getTask(id: Int): TaskDB {
        return taskDao.getTask(id).toTaskDB()
    }

    override fun getAllTasks(): LiveData<List<Task>> {
        val list = taskDao.getAllTasks()
        println("AGM list AGM  = ${list.value}")
        return list
    }

}