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

package me.kshulzh.farm.services

import me.kshulzh.farm.api.FeedService
import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.dto.IngredientTypeDto
import me.kshulzh.farm.dto.ProduceFeedDto
import me.kshulzh.farm.entity.IngredientSum
import me.kshulzh.farm.entity.IngredientType
import me.kshulzh.farm.exception.FeedException
import me.kshulzh.farm.exception.FeedProduceException
import me.kshulzh.farm.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.exception.RecipeNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.mappers.FeedMapper
import me.kshulzh.farm.mappers.FeedRecipeMapper
import me.kshulzh.farm.mappers.IngredientMapper
import me.kshulzh.farm.mappers.IngredientypeMapper
import me.kshulzh.farm.mappers.fromWeigth
import me.kshulzh.farm.mappers.toWeight
import me.kshulzh.farm.repository.FeedRecipeRepository
import me.kshulzh.farm.repository.FeedRepository
import me.kshulzh.farm.repository.IngredientRepository
import me.kshulzh.farm.repository.IngredientSumRepository
import me.kshulzh.farm.repository.IngredientTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FeedServiceImpl : FeedService {
    @Autowired
    lateinit var ingredientRepository: IngredientRepository

    @Autowired
    lateinit var ingredientSumRepository: IngredientSumRepository

    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository

    @Autowired
    lateinit var feedRecipeRepository: FeedRecipeRepository

    @Autowired
    lateinit var feedRepository: FeedRepository

    @Autowired
    lateinit var ingredientypeMapper: IngredientypeMapper

    @Autowired
    lateinit var ingredientMapper: IngredientMapper

    @Autowired
    lateinit var feedRecipeMapper: FeedRecipeMapper

    @Autowired
    lateinit var feedMapper: FeedMapper


    override fun addReceipt(feedRecipe: FeedRecipeDto) {
        val recipe = feedRecipeMapper.toEntity(feedRecipe)
        feedRecipeRepository.save(recipe)
        ingredientTypeRepository.save(IngredientType().apply {
            id = recipe.ingredientId
            name = recipe.name
        })
    }

    override fun editReceipt(feedRecipe: FeedRecipeDto) {
        feedRecipeRepository.save(feedRecipeMapper.toEntity(feedRecipe))
    }

    override fun deleteReceipt(feedRecipeId: String) {
        feedRecipeRepository.deleteById(feedRecipeId) ?: throw RecipeNotFoundException(feedRecipeId)
    }

    override fun getReceipt(feedRecipeId: String): FeedRecipeDto {
        return feedRecipeMapper.toDto(
            feedRecipeRepository.findById(feedRecipeId) ?: throw RecipeNotFoundException(
                feedRecipeId
            )
        )
    }

    override fun getReceipts(criteria: Map<String, String>?): List<FeedRecipeDto> {
        TODO("Not yet implemented")
    }

    override fun addIngredient(ingredient: IngredientTypeDto) {
        ingredientTypeRepository.save(ingredientypeMapper.toEntity(ingredient))
    }

    override fun editIngredient(ingredient: IngredientTypeDto) {
        ingredientTypeRepository.save(ingredientypeMapper.toEntity(ingredient))
    }

    override fun deleteIngredient(ingredientId: String) {
        ingredientTypeRepository.deleteById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
    }

    override fun getIngredient(ingredientId: String): IngredientTypeDto {
        return ingredientypeMapper.toDto(
            ingredientTypeRepository.findById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
        )
    }

    override fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO("Not yet implemented")
    }

    override fun fillStorage(ingredientDto: FillStorageDto) {
        val ingredient = ingredientMapper.toEntity(ingredientDto)
        ingredientRepository.save(ingredient)
        ingredientSumRepository.save(
            ingredientSumRepository.findIngredientSumByTypeId(ingredient.ingredientType.id)?.apply {
                leftovers = toWeight(fromWeigth(leftovers) + fromWeigth(ingredient.count))
            } ?: IngredientSum().apply {
                id = id()
                ingredientType = ingredient.ingredientType
                leftovers = ingredient.count
            }
        )
    }

    override fun getLeftovers(ingredientId: String): List<FillStorageDto> {
        ingredientTypeRepository.findById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
        return ingredientRepository.findIngredientsByTypeId(ingredientId).map { ingredient ->
            ingredientMapper.toDto(ingredient)
        }
    }

    override fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO("Not yet implemented")
    }

    override fun produce(produceFeedDto: ProduceFeedDto) {
        val receipt = feedRecipeRepository.findById(produceFeedDto.receiptId) ?: throw RecipeNotFoundException(
            produceFeedDto.receiptId
        )
        //todo fix if receipt.ingredients == null
        val leftovers = receipt.ingredients!!.map {
            val ingredientSum = ingredientSumRepository.findIngredientSumByTypeId(it.key.id) ?: IngredientSum().apply {
                id = id()
                leftovers = "0"
                ingredientType = it.key
            }
            val left = fromWeigth(ingredientSum.leftovers) - fromWeigth(produceFeedDto.count) * it.value
            if (left < 0.0) {
                throw FeedProduceException(it.key.name ?: it.key.id)
            } else {
                return@map ingredientSum.apply {
                    leftovers = toWeight(left)
                }
            }
        }
        fillStorage(FillStorageDto().apply {
            id = id()
            weght = produceFeedDto.count
            ingredientId = receipt.ingredientId
        })
        leftovers.forEach {
            ingredientSumRepository.save(it)
        }
    }

    override fun feed(feedDto: FeedDto) {
        val feed = feedMapper.toEntity(feedDto)
        val ingredientSum =
            ingredientSumRepository.findIngredientSumByTypeId(feed.ingredientType.id) ?: IngredientSum().apply {
                id = id()
                leftovers = "0"
                ingredientType = feed.ingredientType
            }
        val left = fromWeigth(ingredientSum.leftovers) - fromWeigth(feed.count)
        if (left < 0.0) {
            throw FeedException(feed.ingredientType.name ?: feed.ingredientType.id)
        }
        feedRepository.save(feed)
        ingredientSumRepository.save(ingredientSum.apply {
            leftovers = toWeight(left)
        })
    }
}