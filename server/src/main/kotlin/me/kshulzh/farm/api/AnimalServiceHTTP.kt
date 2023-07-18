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

package me.kshulzh.farm.api

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/animals-service")
interface AnimalServiceHTTP : AnimalService {

    @PostMapping("/animals")
    override fun addAnimal(@RequestBody animal: AnimalDto): AnimalDto

    @DeleteMapping("/animals/{id}")
    override fun deleteAnimal(@PathVariable("id") id: String)

    @PostMapping("/move")
    override fun move(@RequestBody animalInSectionDto: AnimalInSectionDto)

    @GetMapping("/animals/{id}")
    override fun getAnimalById(@PathVariable("id") id: String): AnimalDto

    @GetMapping("/animals")
    override fun getAllAnimals(): List<AnimalDto>

    @PostMapping("/animalsSpecies")
    override fun addAnimalSpecie(@RequestBody animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto

    @GetMapping("/animalsSpecies")
    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto>

}