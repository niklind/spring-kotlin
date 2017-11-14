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
import com.squeed.kotlin.repository.DirectorRepository
import com.squeed.kotlin.repository.MovieRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.toMono

@Component
class HtmlHandler(private val directorRepository: DirectorRepository, private val movieRepository: MovieRepository, private val markdownConverter: MarkdownConverter) {

    fun list(req: ServerRequest) = ok()
            .render("list", mapOf(
                    "title" to "Movie List",
                    "movies" to movieRepository.findAll().flatMap { it.toDto(directorRepository, markdownConverter) }
            ))

    fun movie(req: ServerRequest) = ok()
            .render("movie", mapOf("movie" to movieRepository.findById(req.pathVariable("url")).flatMap { it.toDto(directorRepository, markdownConverter) }.switchIfEmpty(IllegalArgumentException("Wrong movie url provided").toMono())))

}
