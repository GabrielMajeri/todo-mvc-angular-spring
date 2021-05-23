package com.example.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("todos")
class ToDoResource(private val repo: ToDoRepository) {
    @GetMapping
    fun index(): List<ToDo> = repo.findAll().toList()

    @PostMapping
    fun addOrUpdate(@RequestBody toDo: ToDo): ToDo = repo.save(toDo)

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): ToDo {
        val toDo = repo.findById(id)
        return toDo.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "To do not found") }
    }

    @DeleteMapping("{id}")
    fun remove(@PathVariable id: Long) {
        val toDo = repo.findById(id)
        toDo.ifPresentOrElse(
            { repo.delete(it) },
            { throw ResponseStatusException(HttpStatus.NOT_FOUND, "To do not found") }
        )
    }
}
