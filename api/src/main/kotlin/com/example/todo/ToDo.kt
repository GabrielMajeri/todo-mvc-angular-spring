package com.example.todo

import org.springframework.data.annotation.Id

data class ToDo(
    @Id val id: Long? = null,
    val title: String,
    val description: String = "",
    val done: Boolean = false
)
