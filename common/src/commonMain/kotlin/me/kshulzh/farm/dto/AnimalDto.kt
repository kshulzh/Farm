package me.kshulzh.farm.dto

import me.kshulzh.farm.entity.AnimalState
import me.kshulzh.farm.entity.AnimalType
import me.kshulzh.farm.entity.Gender

class AnimalDto : EntityDto() {
    var gender: Gender = Gender.NONE
    var weight: Double? = null
    var specieId: String? = null
    var type: AnimalType? = null
    var state: AnimalState? = null
}