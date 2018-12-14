package com.github.mirjahal.repository

import com.github.mirjahal.model.Note
import org.springframework.data.repository.CrudRepository

interface NoteRepository: CrudRepository<Note, String>