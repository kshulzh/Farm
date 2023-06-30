package me.kshulzh.farm.controller

import me.kshulzh.farm.api.AnimalServiceHTTP
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
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
}