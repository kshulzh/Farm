package me.kshulzh.farm.entity

import java.time.LocalDateTime

class AnimalsInSection : Entity() {
    lateinit var startDate: LocalDateTime
    var endDate: LocalDateTime? = null
    lateinit var animal: Animal
    var section: Section? = null
}