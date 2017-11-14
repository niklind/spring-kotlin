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

import com.squeed.kotlin.repository.UserRepository
import org.springframework.stereotype.Component

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body

@Component
class UserHandler(private val repository: UserRepository) {

    fun findAll(req: ServerRequest) =
            ok().body(repository.findAll())

    fun findOne(req: ServerRequest) =
            ok().body(repository.findById(req.pathVariable("login")))

}
