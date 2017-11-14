package com.squeed.kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Movie(
    @Id val url: String,
    val title: String,
    val headline: String,
    val plot: String,
    val director: String,
    val releaseDate: LocalDateTime = LocalDateTime.now())
