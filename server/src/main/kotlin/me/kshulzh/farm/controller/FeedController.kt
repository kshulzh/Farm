package me.kshulzh.farm.controller

import me.kshulzh.farm.api.FeedServiceHTTP
import me.kshulzh.farm.dto.*
import org.springframework.web.bind.annotation.*

@RestController
class FeedController : FeedServiceHTTP {
    override fun addReceipt(feedRecipe: FeedRecipeDto) {
        TODO()
    }
    override fun editReceipt(feedRecipe: FeedRecipeDto) {
        TODO()
    }
    override fun deleteReceipt(feedRecipeId: String) {
        TODO()
    }
    override fun getReceipt(feedRecipeId: String) : FeedRecipeDto {
        TODO()
    }
    override fun getReceipts(criteria: Map<String,String>?) : List<FeedRecipeDto> {
        TODO()
    }

    override fun addIngredient(ingredient: FeedIngredientDto) {
        TODO()
    }

    override fun editIngredient(ingredient: FeedIngredientDto) {
        TODO()
    }

    override fun deleteIngredient(ingredientId: String) {
        TODO()
    }
    override fun getIngredient(ingredientId: String) : FeedIngredientDto {
        TODO()
    }
    override fun getIngredients(criteria: Map<String,String>?) : List<FeedIngredientDto> {
        TODO()
    }

    override fun fillStorage(ingredient: FillStorageDto) {
        TODO()
    }
    override fun getLeftover(ingredientId: String) : FeedIngredientDto {
        TODO()
    }
    override fun getLeftovers(criteria: Map<String,String>?) : List<FeedIngredientDto> {
        TODO()
    }

    override fun produce(produceFeedDto: ProduceFeedDto) {
        TODO()
    }
    override fun feed(feedDto: FeedDto) {
        TODO()
    }
}