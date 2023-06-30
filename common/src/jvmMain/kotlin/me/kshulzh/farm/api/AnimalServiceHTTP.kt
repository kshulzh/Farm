package me.kshulzh.farm.api

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/animals-service")
interface AnimalServiceHTTP : AnimalService {

    @PostMapping("/animals")
    override fun addAnimal(@RequestBody animal: AnimalDto)

    @DeleteMapping("/animals/{id}")
    override fun deleteAnimal(@PathVariable("id") id: String)

    @PostMapping("/move")
    override fun move(@RequestBody animalInSectionDto: AnimalInSectionDto)

    @GetMapping("/animals/{id}")
    override fun getAnimalById(@PathVariable("id") id: String) : AnimalDto

    @GetMapping("/animals")
    override fun getAllAnimals() : List<AnimalDto>

    @PostMapping("/animalsSpecies")
    override fun addAnimalSpecie(@RequestBody animalSpecieDto: AnimalSpeciesDto) : AnimalSpeciesDto

    @GetMapping("/animalsSpecies")
    override fun getAllAnimalSpecies() : List<AnimalSpeciesDto>

}