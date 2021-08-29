package com.example.lembretes.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lembretes.datasource.repository.TaskRepository

class RegistrationViewModel(
    private val taskRepository: TaskRepository
): ViewModel()

{
    class RegistrationViewModelFactory(private  val taskRepository: TaskRepository):
            ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegistrationViewModel(taskRepository) as T
        }

    }
}