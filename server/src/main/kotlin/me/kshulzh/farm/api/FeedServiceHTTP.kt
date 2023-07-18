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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/feed-service")
interface FeedServiceHTTP : FeedService {
    @PostMapping("/recipes")
    override fun addRecipe(@RequestBody feedRecipe: FeedRecipeDto): FeedRecipeDto

    @PutMapping("/recipes")
    override fun editRecipe(@RequestBody feedRecipe: FeedRecipeDto): FeedRecipeDto

    @DeleteMapping("/recipes/{id}")
    override fun deleteRecipe(@PathVariable("id") feedRecipeId: String)

    @GetMapping("/recipes/{id}")
    override fun getRecipe(@PathVariable("id") feedRecipeId: String): FeedRecipeDto

    @GetMapping("/recipes")
    override fun getRecipes(criteria: Map<String, String>?): List<FeedRecipeDto>

    @PostMapping("/ingredients")
    override fun addIngredient(@RequestBody ingredient: IngredientTypeDto): IngredientTypeDto

    @PutMapping("/ingredients")
    override fun editIngredient(@RequestBody ingredient: IngredientTypeDto): IngredientTypeDto

    @DeleteMapping("/ingredients/{id}")
    override fun deleteIngredient(@PathVariable("id") ingredientId: String)

    @GetMapping("/ingredients/{id}")
    override fun getIngredient(@PathVariable("id") ingredientId: String): IngredientTypeDto

    @GetMapping("/ingredients")
    override fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto>

    @PostMapping("/fill-storage")
    override fun fillStorage(@RequestBody ingredient: FillStorageDto)

    @GetMapping("/leftovers/{id}")
    override fun getLeftovers(@PathVariable("id") ingredientId: String): List<FillStorageDto>

    @GetMapping("/leftovers")
    override fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto>

    @PostMapping("/produce")
    override fun produce(@RequestBody produceFeedDto: ProduceFeedDto)

    @PostMapping("/feed")
    override fun feed(@RequestBody feedDto: FeedDto)
}