package com.squeed.kotlin.repository

import com.squeed.kotlin.model.MovieReleasedEvent
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MovieReleasedEventRepository : ReactiveCrudRepository<MovieReleasedEvent, String> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<MovieReleasedEvent>

}