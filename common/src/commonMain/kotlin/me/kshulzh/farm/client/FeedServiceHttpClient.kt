/*
 *   Copyright (c) 2023. Kyrylo Shulzhenko
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package me.kshulzh.farm.client

import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.dto.IngredientTypeDto
import me.kshulzh.farm.dto.ProduceFeedDto
import me.kshulzh.farm.http.HttpClient


class FeedServiceHttpClient(val httpClient: HttpClient) {
    companion object {
        val FEED_SERVICE_URL = "/feed-service"
        val RECIPES = "/recipes"
        val INGREDIENTS = "/ingredients"
        val FEED = "/feed"
        val PRODUCE = "/produce"
        val FILL_STORAGE = "/fill-storage"
        val LEFTOVERS = "/leftovers"
        val FEED_SERVICE_RECIPES_URL = "$FEED_SERVICE_URL$RECIPES"
        val FEED_SERVICE_INGREDIENTS_URL = "$FEED_SERVICE_URL$INGREDIENTS"
        val FEED_SERVICE_FEED_URL = "$FEED_SERVICE_URL$FEED"
        val FEED_SERVICE_PRODUCE_URL = "$FEED_SERVICE_URL$PRODUCE"
        val FEED_SERVICE_FILL_STORAGE_URL = "$FEED_SERVICE_URL$FILL_STORAGE"
        val FEED_SERVICE_LEFTOVERS_URL = "$FEED_SERVICE_URL$LEFTOVERS"
    }

    suspend fun addRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        return httpClient.post(feedRecipe, FEED_SERVICE_RECIPES_URL, FeedRecipeDto::class)
    }

    suspend fun editRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        return httpClient.put(feedRecipe, FEED_SERVICE_RECIPES_URL, FeedRecipeDto::class)
    }

    suspend fun deleteRecipe(feedRecipeId: String) {
        return httpClient.deleteById(path = FEED_SERVICE_RECIPES_URL, id = feedRecipeId)
    }

    suspend fun getRecipe(feedRecipeId: String): FeedRecipeDto {
        return httpClient.getById(path = FEED_SERVICE_RECIPES_URL, id = feedRecipeId, kClass = FeedRecipeDto::class)
    }

    suspend fun getRecipes(criteria: Map<String, String>?): Array<FeedRecipeDto> {
        return httpClient.get(path = FEED_SERVICE_RECIPES_URL, kClass = Array<FeedRecipeDto>::class)
    }

    suspend fun addIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        return httpClient.post(ingredient, FEED_SERVICE_INGREDIENTS_URL, IngredientTypeDto::class)
    }

    suspend fun editIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        return httpClient.put(ingredient, FEED_SERVICE_INGREDIENTS_URL, IngredientTypeDto::class)
    }

    suspend fun deleteIngredient(ingredientId: String) {
        return httpClient.deleteById(path = FEED_SERVICE_INGREDIENTS_URL, id = ingredientId)
    }

    suspend fun getIngredient(ingredientId: String): IngredientTypeDto {
        return httpClient.getById(
            path = FEED_SERVICE_INGREDIENTS_URL,
            id = ingredientId,
            kClass = IngredientTypeDto::class
        )
    }

    suspend fun getIngredients(criteria: Map<String, String>?): Array<IngredientTypeDto> {
        return httpClient.get(path = FEED_SERVICE_INGREDIENTS_URL, Array<IngredientTypeDto>::class)
    }

    suspend fun fillStorage(ingredient: FillStorageDto) {
        return httpClient.post(ingredient, FEED_SERVICE_FILL_STORAGE_URL)
    }

    suspend fun getLeftovers(ingredientId: String): Array<FillStorageDto> {
        return httpClient.getById(FEED_SERVICE_LEFTOVERS_URL, ingredientId, Array<FillStorageDto>::class)
    }

    suspend fun getLeftovers(criteria: Map<String, String>?): Array<IngredientTypeDto> {
        return httpClient.get(FEED_SERVICE_LEFTOVERS_URL, Array<FillStorageDto>::class)
    }

    suspend fun produce(produceFeedDto: ProduceFeedDto) {
        return httpClient.post(produceFeedDto, FEED_SERVICE_PRODUCE_URL)
    }

    suspend fun feed(feedDto: FeedDto) {
        return httpClient.post(feedDto, FEED_SERVICE_FEED_URL)
    }
}