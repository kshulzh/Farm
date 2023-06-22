package me.kshulzh.farm.dto

class FeedIngredientDto : EntityDto() {
    var type: String = ""
    var price: Double = -1.0
    lateinit var barcode: String
}