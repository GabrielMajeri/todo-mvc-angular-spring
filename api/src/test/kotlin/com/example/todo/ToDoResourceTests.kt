package com.example.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest
class ToDoResourceTests(@Autowired val mockMvc: MockMvc,
                        @Autowired val objectMapper: ObjectMapper) {
    @MockkBean
    private lateinit var toDoRepository: ToDoRepository

    @Test
    fun `root returns list of to dos`() {
        val toDo1 = ToDo(id = 1, title = "To do 1", description = "Test description")
        val toDo2 = ToDo(id = 2, title = "To do 2", description = "Another to do text")
        every { toDoRepository.findAll() } returns listOf(toDo1, toDo2)
        mockMvc.perform(get("/todos"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description").value(toDo1.description))
            .andExpect(jsonPath("$[1].description").value(toDo2.description))
    }

    @Test
    fun `can create to dos`() {
        val toDo = ToDo(title = "Random to do")
        every { toDoRepository.save(toDo) } returns toDo.copy(id = 1)
        val toDoJson = objectMapper.writeValueAsBytes(toDo)
        mockMvc.perform(post("/todos").content(toDoJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value(toDo.title))
    }

    @Test
    fun `can get to do by ID`() {
        val toDo = ToDo(id = 1, title = "Some to do")
        every { toDoRepository.findById(toDo.id!!) } returns Optional.of(toDo)
        mockMvc.perform(get("/todos/{id}", toDo.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(toDo.id))
            .andExpect(jsonPath("$.title").value(toDo.title))
    }

    @Test
    fun `can update to do`() {
        val toDo = ToDo(id = 1, title = "Original text", description = "Test to do")
        val newTitle = "Some new to do title"
        val newToDo = toDo.copy(title = newTitle)
        val newToDoJson = objectMapper.writeValueAsBytes(newToDo)
        every { toDoRepository.save(newToDo) } returns newToDo
        mockMvc.perform(post("/todos")
                .content(newToDoJson)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `can delete to do`() {
        val toDo = ToDo(id = 1, title = "To be deleted")
        every { toDoRepository.findById(toDo.id!!) } returns Optional.of(toDo)
        every { toDoRepository.delete(toDo) } just Runs
        mockMvc.perform(delete("/todos/{id}", 1))
            .andExpect(status().isOk)
    }

    @Test
    fun `cannot delete nonexistent to do`() {
        every { toDoRepository.findById(any()) } returns Optional.empty()
        mockMvc.perform(delete("/todos/{id}", 1))
            .andExpect(status().isNotFound)
    }
}
