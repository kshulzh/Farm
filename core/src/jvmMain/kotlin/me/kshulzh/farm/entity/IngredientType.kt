package me.kshulzh.farm.entity

open class IngredientType : Entity() {
    var type: String = ""
    var price: Double = -1.0
    lateinit var barcode: String
}