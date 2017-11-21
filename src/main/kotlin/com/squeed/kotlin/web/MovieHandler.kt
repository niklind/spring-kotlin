/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squeed.kotlin.web

import com.squeed.kotlin.MarkdownConverter
import com.squeed.kotlin.model.Movie
import com.squeed.kotlin.repository.MovieReleasedEventRepository
import com.squeed.kotlin.repository.MovieRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.toMono

@Component
class MovieHandler(private val movieRepository: MovieRepository,
                   private val movieReleasedEventRepository: MovieReleasedEventRepository,
                   private val markdownConverter: MarkdownConverter) {

    val notifications = movieReleasedEventRepository.count().flatMapMany { movieReleasedEventRepository.findWithTailableCursorBy().skip(it) }.share()

    fun findAll(req: ServerRequest) =
            ok().body(movieRepository.findAll())

    fun findOne(req: ServerRequest) =
            ok().body(req.queryParam("converter")
                    .map {
                        if (it == "markdown")
                            movieRepository.findById(req.pathVariable("url")).map {
                                it.copy(
                                        headline = markdownConverter.invoke(it.headline),
                                        plot = markdownConverter.invoke(it.plot))
                            }
                        else
                            IllegalArgumentException("Only markdown converter is supported").toMono()
                    }
                    .orElse(movieRepository.findById(req.pathVariable("url"))))

    fun save(req: ServerRequest) =
            ok().body(movieRepository.saveAll(req.bodyToMono<Movie>()))

    fun delete(req: ServerRequest) =
            ok().body(movieRepository.deleteById(req.pathVariable("url")))

    fun notifications(req: ServerRequest) =
            ok().bodyToServerSentEvents(notifications)

}
