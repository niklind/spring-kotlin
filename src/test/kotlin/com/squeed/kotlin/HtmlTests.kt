package com.squeed.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test

class HtmlTests : AbstractIntegrationTests() {

    @Test
    fun `Assert content on blog page`() {
        client.get().uri("/").retrieve().bodyToMono<String>()
                .test()
                .consumeNextWith {
                    assertThat(it)
                            .contains("The Godfather")
                            .contains("January 1st")
                            .contains("Francis")
                            .doesNotContain("Steve")
                }.verifyComplete()
    }

    @Test
    fun `Assert content on blog post page`() {
        client.get().uri("/jurassic-park").retrieve().bodyToMono<String>()
                .test()
                .consumeNextWith {
                    assertThat(it)
                            .contains("Jurassic Park")
                            .contains("amok")
                            .contains("cloned dinosaur")
                            .contains("Stephen")
                            .doesNotContain("Uwe")
                }.verifyComplete()
    }

}
