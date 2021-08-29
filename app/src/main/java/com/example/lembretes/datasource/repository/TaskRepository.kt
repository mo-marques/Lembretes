package com.example.lembretes.datasource.repository

import android.media.MediaMetadataRetriever
import com.example.lembretes.model.Task
import com.example.lembretes.model.TaskDB
import com.example.lembretes.ui.RegistrationViewParams
import androidx.lifecycle.LiveData

interface TaskRepository {
    suspend fun createTaks(registrationViewParams: RegistrationViewParams)
    fun getTask(id: Int): TaskDB
    fun getTasks(): LiveData<List<Task>>
    suspend fun getTasksList(): List<Task>

}