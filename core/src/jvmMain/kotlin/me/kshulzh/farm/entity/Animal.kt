package me.kshulzh.farm.entity

open class Animal : Entity() {
    var gender: Gender = Gender.NONE
    var weight: Double? = null
    var specie: AnimalSpecies? = null
    var type: AnimalType? = null
    var section: Section? = null
}