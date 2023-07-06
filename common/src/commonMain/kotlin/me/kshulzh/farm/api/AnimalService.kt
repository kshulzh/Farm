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

interface AnimalService {

    /**
     * add animal to db
     *
     * @param animal
     * @return void
     */
    fun addAnimal(animal: AnimalDto)

    /**
     * remove animal from db
     *
     * @param id
     * @return void
     */
    fun deleteAnimal(id: String)

    /**
     * Move animal from section to section if from is null it is add to section if to is null remove from sections
     *
     * @param animalInSectionDto

     * @return void
     */
    fun move(animalInSectionDto: AnimalInSectionDto)

    /**
     * returns animal by id
     *
     * @param id animal id
     * @return AnimalDto
     */

    fun getAnimalById(id: String): AnimalDto

    fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto

    fun getAllAnimals(): List<AnimalDto>

    fun getAllAnimalSpecies(): List<AnimalSpeciesDto>
}