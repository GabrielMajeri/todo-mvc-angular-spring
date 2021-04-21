package com.example.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
        every { toDoRepository.save(eq(toDo)) } returns ToDo(id = 1, text = toDo.text)
        val toDoJson = objectMapper.writeValueAsBytes(toDo)
        mockMvc.perform(post("/").content(toDoJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.text").value(toDo.text))
    }
}
