package me.kshulzh.farm.utils

import me.kshulzh.farm.entity.Animal
import me.kshulzh.farm.entity.AnimalType
import kotlin.random.Random

var random = Random(1)
fun generateAnimalType(seed: ULong) = AnimalType.values().get(seed.toInt()%AnimalType.values().size)
fun generateAnimal(seed: ULong) = Animal().apply {
    type = generateAnimalType(seed)
}

