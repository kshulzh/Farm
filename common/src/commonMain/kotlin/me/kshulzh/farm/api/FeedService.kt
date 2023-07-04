package me.kshulzh.farm.api

import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.dto.IngredientTypeDto
import me.kshulzh.farm.dto.ProduceFeedDto

interface FeedService {
    fun addReceipt(feedRecipe: FeedRecipeDto)
    fun editReceipt(feedRecipe: FeedRecipeDto)
    fun deleteReceipt(feedRecipeId: String)
    fun getReceipt(feedRecipeId: String): FeedRecipeDto
    fun getReceipts(criteria: Map<String, String>?): List<FeedRecipeDto>

    fun addIngredient(ingredient: IngredientTypeDto)
    fun editIngredient(ingredient: IngredientTypeDto)
    fun deleteIngredient(ingredientId: String)
    fun getIngredient(ingredientId: String): IngredientTypeDto
    fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto>

    fun fillStorage(ingredient: FillStorageDto)

    fun getLeftovers(ingredientId: String): List<FillStorageDto>
    fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto>

    fun produce(produceFeedDto: ProduceFeedDto)
    fun feed(feedDto: FeedDto)
}