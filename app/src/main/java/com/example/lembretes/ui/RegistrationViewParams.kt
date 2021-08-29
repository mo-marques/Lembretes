package com.example.lembretes.ui

import androidx.room.PrimaryKey

data class RegistrationViewParams (
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var hour: String = "",
    var date: String = ""
)