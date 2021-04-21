package com.example.todo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ToDoResource(private val repo: ToDoRepository) {
    @GetMapping
    fun index(): List<ToDo> = repo.findAll().toList()

    @PostMapping
    fun add(@RequestBody toDo: ToDo): ToDo = repo.save(toDo)
}
