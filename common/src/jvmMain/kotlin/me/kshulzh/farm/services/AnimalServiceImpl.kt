package me.kshulzh.farm.services

import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.MoveAnimalDto
import me.kshulzh.farm.repository.AnimalRepository

class AnimalServiceImpl : AnimalService {
    lateinit var animalRepository: AnimalRepository
    override fun add(animal: AnimalDto) {
        TODO("Not yet implemented")
    }

    override fun remove(id: String) {
        TODO("Not yet implemented")
    }

    override fun move(moveAnimalDto: MoveAnimalDto) {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): AnimalDto {
        TODO("Not yet implemented")
    }
}