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