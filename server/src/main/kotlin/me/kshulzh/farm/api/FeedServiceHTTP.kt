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

import me.kshulzh.farm.dto.*
import org.springframework.web.bind.annotation.*

@RequestMapping("/feed-service")
interface FeedServiceHTTP : FeedService {
    @PostMapping("/receipts")
    override fun addReceipt(@RequestBody feedRecipe: FeedRecipeDto)
    @PutMapping("/receipts")
    override fun editReceipt(@RequestBody feedRecipe: FeedRecipeDto)
    @DeleteMapping("/receipts/{id}")
    override fun deleteReceipt(@PathVariable("id") feedRecipeId: String)
    @GetMapping("/receipts/{id}")
    override fun getReceipt(@PathVariable("id") feedRecipeId: String) : FeedRecipeDto
    @GetMapping("/receipts")
    override fun getReceipts(criteria: Map<String,String>?) : List<FeedRecipeDto>

    @PostMapping("/ingredients")
    override fun addIngredient(@RequestBody ingredient: IngredientTypeDto)
    @PutMapping("/ingredients")
    override fun editIngredient(@RequestBody ingredient: IngredientTypeDto)
    @DeleteMapping("/ingredients/{id}")
    override fun deleteIngredient(@PathVariable("id") ingredientId: String)
    @GetMapping("/ingredients/{id}")
    override fun getIngredient(@PathVariable("id") ingredientId: String) : IngredientTypeDto
    @GetMapping("/ingredients")
    override fun getIngredients(criteria: Map<String,String>?) : List<IngredientTypeDto>

    @PostMapping("/fill-storage")
    override fun fillStorage(@RequestBody ingredient: FillStorageDto)
    @GetMapping("/leftovers/{id}")
    override fun getLeftovers(@PathVariable("id") ingredientId: String): List<FillStorageDto>

    @GetMapping("/leftovers")
    override fun getLeftovers(criteria: Map<String,String>?) : List<IngredientTypeDto>

    @PostMapping("/produce")
    override fun produce(@RequestBody produceFeedDto: ProduceFeedDto)
    @PostMapping("/feed")
    override fun feed(@RequestBody feedDto: FeedDto)
}