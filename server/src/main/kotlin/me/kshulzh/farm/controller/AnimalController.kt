package me.kshulzh.farm.controller

import me.kshulzh.farm.api.AnimalServiceHTTP
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import org.springframework.web.bind.annotation.*

@RestController
class AnimalController : AnimalServiceHTTP {

    override fun addAnimal(@RequestBody animal: AnimalDto) {
        TODO()
    }

    override fun deleteAnimal(@PathVariable("id") id: String) {
        TODO()
    }

    override fun move(@RequestBody animalInSectionDto: AnimalInSectionDto) {
        TODO()
    }

    override fun getAnimalById(id: String) : AnimalDto {
        TODO()
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