package com.example.lembretes.datasource.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lembretes.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Int): Task

    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task")
    suspend fun getTasksList(): List<Task>




}