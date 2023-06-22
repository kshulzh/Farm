package me.kshulzh.farm.entity

open class Animal : Entity() {
    var gender: Gender = Gender.NONE
    var weight: Double = -1.0
    var specie: AnimalSpecies? = null
    var type: AnimalType? = null
}