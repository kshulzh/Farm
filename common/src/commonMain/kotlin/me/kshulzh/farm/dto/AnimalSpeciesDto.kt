package me.kshulzh.farm.dto

import me.kshulzh.farm.entity.AnimalType

class AnimalSpeciesDto : EntityDto() {
    lateinit var animalType: AnimalType
    lateinit var description: String
}