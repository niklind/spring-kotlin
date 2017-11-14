package com.squeed.kotlin.repository

import com.squeed.kotlin.model.PostEvent
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PostEventRepository : ReactiveCrudRepository<PostEvent, String> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<PostEvent>

}