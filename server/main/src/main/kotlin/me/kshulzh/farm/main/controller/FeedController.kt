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

package me.kshulzh.farm.main.controller

import me.kshulzh.farm.main.api.FeedService
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.FEED
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.FEED_SERVICE_URL
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.FILL_STORAGE
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.INGREDIENTS
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.LEFTOVERS
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.PRODUCE
import me.kshulzh.farm.main.client.FeedServiceHttpClient.Companion.RECIPES
import me.kshulzh.farm.main.dto.FeedDto
import me.kshulzh.farm.main.dto.FeedRecipeDto
import me.kshulzh.farm.main.dto.FillStorageDto
import me.kshulzh.farm.main.dto.IngredientTypeDto
import me.kshulzh.farm.main.dto.ProduceFeedDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(FEED_SERVICE_URL)
class FeedController : FeedService {
    @Autowired
    lateinit var feedService: FeedService
    @PostMapping(RECIPES)
    override fun addRecipe(@RequestBody feedRecipe: FeedRecipeDto): FeedRecipeDto {
        return feedService.addRecipe(feedRecipe)
    }


    @PutMapping(RECIPES)
    override fun editRecipe(@RequestBody feedRecipe: FeedRecipeDto): FeedRecipeDto {
        return feedService.editRecipe(feedRecipe)
    }


    @DeleteMapping("$RECIPES/{id}")
    override fun deleteRecipe(@PathVariable("id") feedRecipeId: String) {
        feedService.deleteRecipe(feedRecipeId)
    }


    @GetMapping("$RECIPES/{id}")
    override fun getRecipe(@PathVariable("id") feedRecipeId: String): FeedRecipeDto {
        return feedService.getRecipe(feedRecipeId)
    }


    @GetMapping(RECIPES)
    override fun getRecipes(criteria: Map<String, String>?): List<FeedRecipeDto> {
        TODO()
    }


    @PostMapping(INGREDIENTS)
    override fun addIngredient(@RequestBody ingredient: IngredientTypeDto): IngredientTypeDto {
        return feedService.addIngredient(ingredient)
    }


    @PutMapping(INGREDIENTS)
    override fun editIngredient(@RequestBody ingredient: IngredientTypeDto): IngredientTypeDto {
        return feedService.editIngredient(ingredient)
    }


    @DeleteMapping("$INGREDIENTS/{id}")
    override fun deleteIngredient(@PathVariable("id") ingredientId: String) {
        feedService.deleteRecipe(ingredientId)
    }


    @GetMapping("$INGREDIENTS/{id}")
    override fun getIngredient(@PathVariable("id") ingredientId: String): IngredientTypeDto {
        return feedService.getIngredient(ingredientId)
    }


    @GetMapping(INGREDIENTS)
    override fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO()
    }


    @PostMapping(FILL_STORAGE)
    override fun fillStorage(@RequestBody ingredient: FillStorageDto) {
        feedService.fillStorage(ingredient)
    }


    @GetMapping("$LEFTOVERS/{id}")
    override fun getLeftovers(@PathVariable("id") ingredientId: String): List<FillStorageDto> {
        return feedService.getLeftovers(ingredientId)
    }


    @GetMapping(LEFTOVERS)
    override fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO()
    }


    @PostMapping(PRODUCE)
    override fun produce(@RequestBody produceFeedDto: ProduceFeedDto) {
        feedService.produce(produceFeedDto)
    }


    @PostMapping(FEED)
    override fun feed(@RequestBody feedDto: FeedDto) {
        feedService.feed(feedDto)
    }

}