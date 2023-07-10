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

package me.kshulzh.farm.controller

import me.kshulzh.farm.api.FeedServiceHTTP
import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.dto.IngredientTypeDto
import me.kshulzh.farm.dto.ProduceFeedDto
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedController : FeedServiceHTTP {
    override fun addRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        TODO()
    }

    override fun editRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        TODO()
    }

    override fun deleteRecipe(feedRecipeId: String) {
        TODO()
    }

    override fun getRecipe(feedRecipeId: String): FeedRecipeDto {
        TODO()
    }

    override fun getRecipes(criteria: Map<String, String>?): List<FeedRecipeDto> {
        TODO()
    }

    override fun addIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        TODO()
    }

    override fun editIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        TODO()
    }

    override fun deleteIngredient(ingredientId: String) {
        TODO()
    }

    override fun getIngredient(ingredientId: String): IngredientTypeDto {
        TODO()
    }

    override fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO()
    }

    override fun fillStorage(ingredient: FillStorageDto) {
        TODO()
    }

    override fun getLeftovers(ingredientId: String): List<FillStorageDto> {
        TODO("Not yet implemented")
    }

    override fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO()
    }

    override fun produce(produceFeedDto: ProduceFeedDto) {
        TODO()
    }

    override fun feed(feedDto: FeedDto) {
        TODO()
    }
}