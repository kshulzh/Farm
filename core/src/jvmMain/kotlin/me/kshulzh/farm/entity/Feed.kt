package me.kshulzh.farm.entity

import java.time.LocalDateTime

class Feed : Entity() {
    lateinit var ingredientType: IngredientType
    lateinit var count: String
    lateinit var section: Section
    lateinit var time: LocalDateTime
}