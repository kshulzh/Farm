package me.kshulzh.farm.dto

class IngredientTypeDto : EntityDto() {
    var name: String? = null
    var type: String = ""
    var price: Double? = null
    var description: String? = null
    var date: String? = null
    var barcode: String? = null
}