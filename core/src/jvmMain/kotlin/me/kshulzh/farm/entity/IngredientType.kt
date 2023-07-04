package me.kshulzh.farm.entity

import java.time.LocalDateTime

open class IngredientType : Entity() {
    var type: String = ""
    var name: String? = null
    var price: Double? = null
    var description: String? = null
    lateinit var date: LocalDateTime
    var barcode: String? = null
}