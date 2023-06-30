package me.kshulzh.farm.services

import me.kshulzh.farm.api.FeedService
import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.dto.FeedIngredientDto
import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.dto.ProduceFeedDto

class FeedServiceImpl : FeedService {
    override fun addReceipt(feedRecipe: FeedRecipeDto) {
        TODO("Not yet implemented")
    }

    override fun editReceipt(feedRecipe: FeedRecipeDto) {
        TODO("Not yet implemented")
    }

    override fun deleteReceipt(feedRecipeId: String) {
        TODO("Not yet implemented")
    }

    override fun getReceipt(feedRecipeId: String): FeedRecipeDto {
        TODO("Not yet implemented")
    }

    override fun getReceipts(criteria: Map<String, String>?): List<FeedRecipeDto> {
        TODO("Not yet implemented")
    }

    override fun addIngredient(ingredient: FeedIngredientDto) {
        TODO("Not yet implemented")
    }

    override fun editIngredient(ingredient: FeedIngredientDto) {
        TODO("Not yet implemented")
    }

    override fun deleteIngredient(ingredientId: String) {
        TODO("Not yet implemented")
    }

    override fun getIngredient(ingredientId: String): FeedIngredientDto {
        TODO("Not yet implemented")
    }

    override fun getIngredients(criteria: Map<String, String>?): List<FeedIngredientDto> {
        TODO("Not yet implemented")
    }

    override fun fillStorage(ingredient: FillStorageDto) {
        TODO("Not yet implemented")
    }

    override fun getLeftover(ingredientId: String): FeedIngredientDto {
        TODO("Not yet implemented")
    }

    override fun getLeftovers(criteria: Map<String, String>?): List<FeedIngredientDto> {
        TODO("Not yet implemented")
    }

    override fun produce(produceFeedDto: ProduceFeedDto) {
        TODO("Not yet implemented")
    }

    override fun feed(feedDto: FeedDto) {
        TODO("Not yet implemented")
    }
}