package me.kshulzh.farm.api

import me.kshulzh.farm.dto.*

interface FeedService {
    fun addReceipt(feedRecipe: FeedRecipeDto)
    fun editReceipt(feedRecipe: FeedRecipeDto)
    fun deleteReceipt(feedRecipeId: String)
    fun getReceipt(feedRecipeId: String) : FeedRecipeDto
    fun getReceipts(criteria: Map<String,String>?) : List<FeedRecipeDto>

    fun addIngredient(ingredient: FeedIngredientDto)
    fun editIngredient(ingredient: FeedIngredientDto)
    fun deleteIngredient(ingredientId: String)
    fun getIngredient(ingredientId: String) : FeedIngredientDto
    fun getIngredients(criteria: Map<String,String>?) : List<FeedIngredientDto>

    fun fillStorage(ingredient: FillStorageDto)
    fun getLeftover(ingredientId: String) : FeedIngredientDto
    fun getLeftovers(criteria: Map<String,String>?) : List<FeedIngredientDto>

    fun produce(produceFeedDto: ProduceFeedDto)
    fun feed(feedDto: FeedDto)
}