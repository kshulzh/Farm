package me.kshulzh.farm.entity

open class FeedRecipe : Entity() {
    var description: String? = null;
    var ingredients: Map<IngredientType,Double> = mutableMapOf()
}