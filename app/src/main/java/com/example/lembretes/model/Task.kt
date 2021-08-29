package com.example.lembretes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lembretes.ui.RegistrationViewParams

@Entity(tableName = "task")
data class Task(
    val title: String,
    val description: String,
    val hour: String,
    val date: String,
    @PrimaryKey val id: Int = 0
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
            title = this.title,
            description = this.description,
            date = this.date,
            hour = this.hour
        )
    }
}

fun Task.toTaskDB(): TaskDB {
    return TaskDB(
        id = this.id.toString(),
        title = this.title,
        description = this.description,
        date = this.date,
        hour = this.hour
    )

}
