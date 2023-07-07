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

package me.kshulzh.farm.client

import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto

class AnimalServiceImpl : AnimalService {
    override fun addAnimal(animal: AnimalDto) {

        TODO("Not yet implemented")
    }

    override fun deleteAnimal(id: String) {
        TODO("Not yet implemented")
    }

    override fun move(animalInSectionDto: AnimalInSectionDto) {
        TODO("Not yet implemented")
    }

    override fun getAnimalById(id: String): AnimalDto {
        TODO("Not yet implemented")
    }

    override fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        TODO("Not yet implemented")
    }

    override fun getAllAnimals(): List<AnimalDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto> {
        TODO("Not yet implemented")
    }
}