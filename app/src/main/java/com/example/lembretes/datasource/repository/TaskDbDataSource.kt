package com.example.lembretes.datasource.repository

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
    }

    override fun getTask(id: Int): TaskDB {
        return taskDao.getTask(id).toTaskDB()
    }
}