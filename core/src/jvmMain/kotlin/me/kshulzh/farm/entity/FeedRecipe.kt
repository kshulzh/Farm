package me.kshulzh.farm.entity

open class FeedRecipe : Entity() {
    lateinit var ingredientId: String
    var name: String? = null
    var description: String? = null
    var ingredients: Map<IngredientType, Double>? = null
}