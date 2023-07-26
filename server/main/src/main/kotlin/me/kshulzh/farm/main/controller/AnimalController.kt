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

package me.kshulzh.farm.main.controller

import me.kshulzh.farm.main.api.AnimalService
import me.kshulzh.farm.main.client.AnimalServiceHttpClient.Companion.ANIMALS
import me.kshulzh.farm.main.client.AnimalServiceHttpClient.Companion.ANIMALS_SPECIES
import me.kshulzh.farm.main.client.AnimalServiceHttpClient.Companion.ANIMAL_SERVICE_URL
import me.kshulzh.farm.main.client.AnimalServiceHttpClient.Companion.MOVE
import me.kshulzh.farm.main.dto.AnimalDto
import me.kshulzh.farm.main.dto.AnimalInSectionDto
import me.kshulzh.farm.main.dto.AnimalSpeciesDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ANIMAL_SERVICE_URL)
class AnimalController : AnimalService {
    lateinit var animalService: AnimalService
    @PostMapping(ANIMALS)
    override fun addAnimal(@RequestBody animal: AnimalDto): AnimalDto {
        return animalService.addAnimal(animal)
    }

    @DeleteMapping("$ANIMALS/{id}")
    override fun deleteAnimal(@PathVariable("id") id: String) {
        animalService.deleteAnimal(id)
    }

    @PostMapping(MOVE)
    override fun move(@RequestBody animalInSectionDto: AnimalInSectionDto) {
        animalService.move(animalInSectionDto)
    }

    @GetMapping("$ANIMALS/{id}")
    override fun getAnimalById(@PathVariable("id") id: String): AnimalDto {
        return animalService.getAnimalById(id)
    }

    @GetMapping(ANIMALS)
    override fun getAllAnimals(): List<AnimalDto> {
        return animalService.getAllAnimals()
    }

    @PostMapping(ANIMALS_SPECIES)
    override fun addAnimalSpecie(@RequestBody animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        return animalService.addAnimalSpecie(animalSpecieDto)
    }

    @GetMapping(ANIMALS_SPECIES)
    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto> {
        return animalService.getAllAnimalSpecies()
    }
}