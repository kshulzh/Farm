package me.kshulzh.farm.api

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.MoveAnimalDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/animals-service")
interface AnimalServiceHTTP : AnimalService {

    @PostMapping("/animals")
    override fun add(@RequestBody animal: AnimalDto)

    @DeleteMapping("/animals/{id}")
    override fun remove(@PathVariable("id") id: String)

    @PostMapping("/move")
    override fun move(@RequestBody moveAnimalDto: MoveAnimalDto)

    @GetMapping("/animals/{id}")
    override fun getById(@PathVariable("id") id: String) : AnimalDto
}