/*
 *   Copyright (c) 2023. Kyrylo Shulzhenko
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package me.kshulzh.farm.controller

import me.kshulzh.farm.api.AnimalServiceHTTP
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class AnimalController : AnimalServiceHTTP {

    override fun addAnimal(@RequestBody animal: AnimalDto): AnimalDto {
        println(animal)
        return animal
    }

    override fun deleteAnimal(@PathVariable("id") id: String) {
        TODO()
    }

    override fun move(@RequestBody animalInSectionDto: AnimalInSectionDto) {
        TODO()
    }

    override fun getAnimalById(id: String): AnimalDto {
        println(LocalDateTime.now())
        return AnimalDto()
    }

    override fun getAllAnimals(): List<AnimalDto> {
        TODO("Not yet implemented")
    }

    override fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        TODO("Not yet implemented")
    }

    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto> {
        TODO("Not yet implemented")
    }
}