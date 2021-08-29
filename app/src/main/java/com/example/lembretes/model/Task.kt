package com.example.lembretes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lembretes.ui.RegistrationViewParams

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "hour") val hour: String,
    @ColumnInfo(name = "date") val date: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}

fun RegistrationViewParams.toTask(): Task {
    return with(this) {
        Task(
            id = this.id,
            title = this.title,
            description = this.description,
            date = this.date,
            hour = this.hour
        )
    }
}

fun Task.toTaskDB(): TaskDB {
    return TaskDB(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        hour = this.hour
    )

}
