package me.kshulzh.farm.controller

import me.kshulzh.farm.api.AnimalServiceHTTP
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.MoveAnimalDto
import org.springframework.web.bind.annotation.*

@RestController
class AnimalController : AnimalServiceHTTP {

    override fun add(@RequestBody animal: AnimalDto) {
        TODO()
    }

    override fun remove(@PathVariable("id") id: String) {
        TODO()
    }

    override fun move(@RequestBody moveAnimalDto: MoveAnimalDto) {
        TODO()
    }

    override fun getById(id: String) : AnimalDto {
        TODO()
    }
}