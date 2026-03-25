package com.example.aslzoda_nav_drawer_toolbar.data

data class Task(

    val id: Int,

    val title: String,
    val description: String,
    val deadline: Long,
    val priority: String,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
