package com.github.mirjahal.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

const val PK_GENERATOR: String = "uuid2"

@Entity
data class Note(@Id
                @GeneratedValue(generator = PK_GENERATOR)
                @GenericGenerator(name = PK_GENERATOR, strategy = PK_GENERATOR)
                @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
                val id: String = "",
                val title: String = "",
                val description: String = "")