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

import com.squeed.kotlin.model.Director

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

class DirectorJsonApiTests : AbstractIntegrationTests() {

    @Test
    fun `Assert FindAll JSON API is parsed correctly and contains 11 elements`() {
        client.get().uri("/api/director/").retrieve().bodyToFlux<Director>()
                .test()
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun `Verify findOne JSON API`() {
        client.get().uri("/api/director/boll").retrieve().bodyToMono<Director>()
                .test()
                .consumeNextWith {
                    assertThat(it.id).isEqualTo("boll")
                    assertThat(it.firstname).isEqualTo("Uwe")
                    assertThat(it.lastname).isEqualTo("Boll")
                    assertThat(it.description).isEqualTo(null)
                }.verifyComplete()
    }

}
