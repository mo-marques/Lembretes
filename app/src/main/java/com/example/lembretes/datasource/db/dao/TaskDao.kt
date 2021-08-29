package com.example.lembretes.datasource.db.dao

import androidx.room.*
import com.example.lembretes.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Int): Task




}