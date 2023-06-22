package me.kshulzh.farm.api

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.MoveAnimalDto

interface AnimalService {

    /**
     * add animal to db
     *
     * @param animal
     * @return void
     */
    fun add(animal: AnimalDto)

    /**
     * remove animal from db
     *
     * @param id
     * @return void
     */
    fun remove(id: String)

    /**
     * Move animal from section to section if from is null it is add to section if to is null remove from sections
     *
     * @param moveAnimalDto

     * @return void
     */
    fun move(moveAnimalDto: MoveAnimalDto)

    /**
     * returns animal by id
     *
     * @param id animal id
     * @return AnimalDto
     */

    fun getById(id: String) : AnimalDto
}