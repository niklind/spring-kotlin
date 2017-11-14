package com.squeed.kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class MovieReleasedEvent(@Id val url: String, val title: String)