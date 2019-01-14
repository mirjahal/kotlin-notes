package com.github.mirjahal.controller

import com.github.mirjahal.model.Note
import com.github.mirjahal.repository.NoteRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(NoteController::class)
class NoteControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var noteRepository: NoteRepository

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun noteRepository() = mockk<NoteRepository>()
    }

    @Test
    fun `deve validar criacao de nota`() {
        val noteToInsert : Note = Note(id = "", title = "Note 1", description  = "Note 1 description")
        val mockedNote : Note = Note(id = "978sda798ads9adas88", title = "Note 1", description  = "Note 1 description")

        every {
            noteRepository.save(noteToInsert)
        } returns mockedNote

        val requestBuilder : MockHttpServletRequestBuilder = post("/notes")
                .content("{ \"title\": \"Note 1\", \"description\": \"Note 1 description\" }")
                .contentType(MediaType.APPLICATION_JSON)

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id").value("978sda798ads9adas88"))
                .andExpect(jsonPath("$.title").value("Note 1"))
                .andExpect(jsonPath("$.description").value("Note 1 description"))
    }

}