package com.example.lembretes.model

data class TaskDB (
    private val id: String,
    private val title: String,
    private val description: String,
    private val hour: String,
    private val date: String
)