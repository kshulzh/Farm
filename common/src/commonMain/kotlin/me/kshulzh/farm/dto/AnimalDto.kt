package me.kshulzh.farm.dto

import me.kshulzh.farm.entity.*

class AnimalDto : EntityDto() {
    var gender: Gender = Gender.NONE
    var weight: Double? = null
    var specie: AnimalSpecies? = null
    var type: AnimalType? = null
    var state: AnimalState? = null
    lateinit var count:String
}