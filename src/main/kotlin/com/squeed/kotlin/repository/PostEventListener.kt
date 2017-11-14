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
package com.squeed.kotlin.repository

import io.spring.deepdive.model.Post
import io.spring.deepdive.model.PostEvent
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent
import org.springframework.stereotype.Component

@Component
class PostEventListener(private val postEventRepository: PostEventRepository) : AbstractMongoEventListener<Post>() {

    override fun onAfterSave(event: AfterSaveEvent<Post>) {
        postEventRepository.save(PostEvent(event.source.slug, event.source.title)).block()
    }

}
