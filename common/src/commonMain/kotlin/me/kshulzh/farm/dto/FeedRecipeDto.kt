package me.kshulzh.farm.dto

class FeedRecipeDto : EntityDto() {
    var ingredientId: String? = null
    var name: String? = null
    var description: String? = null
    var ingredients: Map<String, Double>? = null
}