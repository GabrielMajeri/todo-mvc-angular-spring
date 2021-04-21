package com.example.todo

import org.springframework.data.annotation.Id

data class ToDo(@Id val id: Long? = null, val text: String)
