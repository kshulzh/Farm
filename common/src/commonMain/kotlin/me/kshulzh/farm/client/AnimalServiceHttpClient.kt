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

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import me.kshulzh.farm.http.HttpClient
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

class AnimalServiceHttpClient(val httpClient: HttpClient) {
    companion object {
        val ANIMAL_SERVICE_URL = "/animals-service"
        val ANIMALS = "/animals"
        val MOVE = "/move"
        val ANIMALS_SPECIES = "/animalsSpecies"
        val ANIMAL_SERVICE_ANIMALS_URL = "$ANIMAL_SERVICE_URL$ANIMALS"
        val ANIMAL_SERVICE_MOVE_URL = "$ANIMAL_SERVICE_URL$MOVE"
        val ANIMAL_SERVICE_ANIMALS_SPECIES_URL = "$ANIMAL_SERVICE_URL$ANIMALS_SPECIES"
    }

    suspend fun addAnimal(animal: AnimalDto): AnimalDto {
        return httpClient.post(animal, ANIMAL_SERVICE_ANIMALS_URL, AnimalDto::class)
    }

    suspend fun deleteAnimal(id: String) {
        return httpClient.deleteById(path = ANIMAL_SERVICE_ANIMALS_URL, id = id)
    }

    suspend fun move(animalInSectionDto: AnimalInSectionDto) {
        return httpClient.post(animalInSectionDto, ANIMAL_SERVICE_MOVE_URL)
    }

    suspend fun getAnimalById(id: String): AnimalDto {
        return httpClient.getById(ANIMAL_SERVICE_ANIMALS_URL, id, AnimalDto::class)
    }

    suspend fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        return httpClient.post(animalSpecieDto, ANIMAL_SERVICE_ANIMALS_SPECIES_URL, AnimalSpeciesDto::class)
    }

    suspend fun getAllAnimals(): Array<AnimalDto> {
        return httpClient.get(
            ANIMAL_SERVICE_ANIMALS_URL,
            (typeOf<Array<AnimalDto>>().classifier!! as KClass<Array<AnimalDto>>)
        )
    }

    suspend fun getAllAnimalSpecies(): Array<AnimalSpeciesDto> {
        return httpClient.get(
            ANIMAL_SERVICE_ANIMALS_SPECIES_URL,
            (typeOf<Array<AnimalSpeciesDto>>().classifier!! as KClass<Array<AnimalSpeciesDto>>)
        )
    }
}