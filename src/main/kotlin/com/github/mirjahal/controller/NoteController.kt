package com.github.mirjahal.controller

import com.github.mirjahal.model.Note
import com.github.mirjahal.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI

@RestController
@RequestMapping("/notes")
class NoteController {

    @Autowired
    lateinit var noteRepository: NoteRepository

    @GetMapping
    fun list(): ResponseEntity<List<Note>> {
        return ResponseEntity.ok(noteRepository.findAll().toList())
    }

    @PostMapping
    fun add(@RequestBody note: Note): ResponseEntity<Note> {
        val newNote = noteRepository.save(note)
        val noteLocaltion: String = String.format("/notes/%s", newNote.id)

        return ResponseEntity
                .created(URI.create(noteLocaltion))
                .body(newNote)
    }

    @PutMapping("{id}")
    fun alter(@PathVariable id: String, @RequestBody note: Note): ResponseEntity<Note> {
        if (noteRepository.existsById(id)) {
            val safeNote = note.copy(id)
            return ResponseEntity.ok(noteRepository.save(safeNote))
        }

        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id)
        }
    }

}