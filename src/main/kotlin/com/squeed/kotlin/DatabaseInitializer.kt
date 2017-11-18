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
        val coppola2 = Director("coppola2", "Sofia", "Coppola")
        val nolan = Director("nolan", "Christopher", "Nolan")
        val spielberg = Director("spielberg", "Stephen", "Spielberg")
        val scorsese = Director("scorsese", "Martin", "Scorsese")

        val boll = Director("boll", "Uwe", "Boll")

        directorRepository.saveAll(Arrays.asList(coppola, coppola2, nolan, spielberg, scorsese, boll)).blockLast()

        val godfatherTitle = "The Godfather"
        val godfatherPost = Movie(
                godfatherTitle.urlify(),
                godfatherTitle,
                """The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.""".trimMargin(),
                """""".trimMargin(),
                "coppola",
                LocalDateTime.of(1972, 1, 1, 0, 0)
        )

        val lostTitle = "Lost in Translation"
        val lostPost = Movie(
                lostTitle.urlify(),
                lostTitle,
                """A faded movie star and a neglected young woman form an unlikely bond after crossing paths in Tokyo.""".trimMargin(),
                """""".trimMargin(),
                "coppola2",
                LocalDateTime.of(2003, 1, 1, 0, 0)
        )

        val jurassicTitle = "Jurassic Park"
        val jurassicPost = Movie(
                jurassicTitle.urlify(),
                jurassicTitle,
                """During a preview tour, a theme park suffers a major power breakdown that allows its cloned dinosaur exhibits to run amok.""".trimMargin(),
                """""".trimMargin(),
                "spielberg",
                LocalDateTime.of(1993, 1, 1, 0, 0)
        )

        val etTitle = "E.T"
        val etPost = Movie(
                etTitle.urlify(),
                etTitle,
                """A troubled child summons the courage to help a friendly alien escape Earth and return to his home world.""".trimMargin(),
                """""".trimMargin(),
                "spielberg",
                LocalDateTime.of(1982, 1, 1, 0, 0)
        )

        val wolfTitle = "The Wolf of Wall Street"
        val wolfPost = Movie(
                wolfTitle.urlify(),
                wolfTitle,
                """Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.""".trimMargin(),
                """""".trimMargin(),
                "scorsese",
                LocalDateTime.of(2013, 1, 1, 0, 0)
        )

        val batmanTitle = "The Dark Knight"
        val batmanPost = Movie(
                batmanTitle.urlify(),
                batmanTitle,
                """When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham, the Dark Knight must accept one of the greatest psychological and physical tests of his ability to fight injustice.""".trimMargin(),
                """""".trimMargin(),
                "nolan",
                LocalDateTime.of(2008, 1, 1, 0, 0)
        )

        val bluberellaTitle = "Bluberella"
        val bluberellaPost = Movie(
                bluberellaTitle.urlify(),
                bluberellaTitle,
                """An action comedy centered on an overweight woman whose footsteps cause explosions and whose dual swords are used against anyone who makes fun of her.""".trimMargin(),
                """""".trimMargin(),
                "boll",
                LocalDateTime.of(2011, 1, 1, 0, 0)
        )

        movieRepository.saveAll(Arrays.asList(godfatherPost, lostPost, jurassicPost, etPost, wolfPost, batmanPost, bluberellaPost)).blockLast()
    }
}
