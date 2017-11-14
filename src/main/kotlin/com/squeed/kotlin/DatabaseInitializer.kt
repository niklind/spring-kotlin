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
import com.squeed.kotlin.model.Movie
import com.squeed.kotlin.model.MovieReleasedEvent
import com.squeed.kotlin.repository.DirectorRepository
import com.squeed.kotlin.repository.MovieRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*


@Component
class DatabaseInitializer(
        private val ops: MongoOperations,
        private val directorRepository: DirectorRepository,
        private val movieRepository: MovieRepository) : CommandLineRunner {

    override fun run(vararg args: String) {

        ops.createCollection(MovieReleasedEvent::class.java, CollectionOptions.empty().capped().size(10000))

        val coppola = Director("coppola", "Francis", "Coppola")
        val darabont = Director("darabont", "Frank", "Darabont")
        val spielberg = Director("spielberg", "Stephen", "Speielberg")
        val scorsese = Director("scorsese", "Martin", "Scorsese")
        val curtiz = Director("curtiz", "Michael", "Curtiz")

        directorRepository.saveAll(Arrays.asList(coppola, darabont, spielberg, scorsese, curtiz)).blockLast()

        val godfatherTitle = "The Godfather"
        val godfatherPost = Movie(
                godfatherTitle.urlify(),
                godfatherTitle,
                """The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.""".trimMargin(),
                """Some example plot""".trimMargin(),
                "coppola",
                LocalDateTime.of(1972, 1, 1, 0, 0)
        )

        val shawshankTitle = "The Shawshank Redemption"
        val shawshankPost = Movie(
                shawshankTitle.urlify(),
                shawshankTitle,
                """Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.""".trimMargin(),
                """Another example plot""".trimMargin(),
                "darabont",
                LocalDateTime.of(1994, 1, 1, 0, 0)
        )

        val schindlerTitle = "Schindler's List"
        val schindlerPost = Movie(
                schindlerTitle.urlify(),
                schindlerTitle,
                """In German-occupied Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazi Germans.""".trimMargin(),
                """A third example plot""".trimMargin(),
                "darabont",
                LocalDateTime.of(1993, 1, 1, 0, 0)
        )

        movieRepository.saveAll(Arrays.asList(godfatherPost, shawshankPost, schindlerPost)).blockLast()
    }
}
