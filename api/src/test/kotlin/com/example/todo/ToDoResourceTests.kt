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
        val toDo1 = ToDo(id = 1, text = "Test description")
        val toDo2 = ToDo(id = 2, text = "Another to do text")
        every { toDoRepository.findAll() } returns listOf(toDo1, toDo2)
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].text").value(toDo1.text))
            .andExpect(jsonPath("$[1].text").value(toDo2.text))
    }

    @Test
    fun `can create to dos`() {
        val toDo = ToDo(text = "Random description")
        every { toDoRepository.save(toDo) } returns toDo.copy(id = 1)
        val toDoJson = objectMapper.writeValueAsBytes(toDo)
        mockMvc.perform(post("/").content(toDoJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.text").value(toDo.text))
    }

    @Test
    fun `can get to do by ID`() {
        val toDo = ToDo(id = 1, text = "Some text")
        every { toDoRepository.findById(toDo.id!!) } returns Optional.of(toDo)
        mockMvc.perform(get("/{id}", toDo.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(toDo.id))
            .andExpect(jsonPath("$.text").value(toDo.text))
    }

    @Test
    fun `can update to do`() {
        val toDo = ToDo(id = 1, text = "Original text")
        val newText = "Some new to do text"
        val newToDo = toDo.copy(text = newText)
        every { toDoRepository.findById(toDo.id!!) } returns Optional.of(toDo)
        every { toDoRepository.save(newToDo) } returns newToDo
        mockMvc.perform(put("/{id}", toDo.id)
                .content("{ \"text\": \"$newText\" }")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `cannot update to do's ID`() {
        mockMvc.perform(put("/{id}", 1)
                .content("{ \"id\": 50, \"text\": \"Some new text\" }")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
        verify { toDoRepository.save(any()) wasNot Called }
    }

    @Test
    fun `cannot update nonexistent to do`() {
        every { toDoRepository.findById(any()) } returns Optional.empty()
        mockMvc.perform(put("/{id}", 1)
                .content("{ \"text\": \"Some text\" }")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `can delete to do`() {
        val toDo = ToDo(id = 1, text = "To be deleted")
        every { toDoRepository.findById(toDo.id!!) } returns Optional.of(toDo)
        every { toDoRepository.delete(toDo) } just Runs
        mockMvc.perform(delete("/{id}", 1))
            .andExpect(status().isOk)
    }

    @Test
    fun `cannot delete nonexistent to do`() {
        every { toDoRepository.findById(any()) } returns Optional.empty()
        mockMvc.perform(delete("/{id}", 1))
            .andExpect(status().isNotFound)
    }
}
