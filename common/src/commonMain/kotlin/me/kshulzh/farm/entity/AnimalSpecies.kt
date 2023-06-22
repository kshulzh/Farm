package me.kshulzh.farm.entity

import me.kshulzh.farm.dto.EntityDto

class AnimalSpecies : EntityDto() {
    lateinit var animalType: AnimalType
    lateinit var description: String
}