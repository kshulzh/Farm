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

    fun getAnimalById(id: String) : AnimalDto

    fun addAnimalSpecie(animalSpecieDto:AnimalSpeciesDto) : AnimalSpeciesDto

    fun getAllAnimals() : List<AnimalDto>

    fun getAllAnimalSpecies() : List<AnimalSpeciesDto>
}