package com.example.todo

import org.springframework.data.repository.CrudRepository

interface ToDoRepository : CrudRepository<ToDo, Long>
