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
package com.squeed.kotlin

import com.squeed.kotlin.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test
import java.time.LocalDateTime

class MovieJsonApiTests : AbstractIntegrationTests() {

    @Test
    fun `Assert findAll JSON API is parsed correctly and contains 3 elements`() {
        client.get().uri("/api/movie/").retrieve().bodyToFlux<Movie>()
                .test()
                .expectNextCount(7)
                .verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API`() {
        client.get().uri("/api/movie/the-wolf-of-wall-street").retrieve().bodyToMono<Movie>()
                .test()
                .consumeNextWith {
                    assertThat(it.title).isEqualTo("The Wolf of Wall Street")
                    assertThat(it.headline).startsWith("Based on the true story of Jordan Belfort")
                    assertThat(it.plot).isEqualTo("")
                    assertThat(it.releaseDate).isEqualTo(LocalDateTime.of(2013, 1, 1, 0, 0))
                    assertThat(it.director).isEqualTo("scorsese")
                }.verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API with invalid converter`() {
        client.get().uri("/api/movie/reactor-bismuth-is-out?converter=foo").exchange()
                .test()
                .consumeNextWith { assertThat(it.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR) }
                .verifyComplete()
    }

//    @Test
//    fun `Verify movie JSON API and notifications via SSE`() {
//        client.get().uri("/api/movie/notifications").accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux<MovieReleasedEvent>()
//                .take(1)
//                .doOnSubscribe {
//                    client.post().uri("/api/movie/").syncBody(Movie("foo", "Foo", "foo", "foo", "coppola", LocalDateTime.now())).exchange().subscribe()
//                }
//                .test()
//                .consumeNextWith {
//                    assertThat(it.url).isEqualTo("foo")
//                    assertThat(it.title).isEqualTo("Foo")
//                }
//                .verifyComplete()
//    }

}
