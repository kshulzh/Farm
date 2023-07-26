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

package me.kshulzh.farm.main.services

import me.kshulzh.farm.main.entity.IngredientSum
import me.kshulzh.farm.main.entity.IngredientType
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.FeedDto
import me.kshulzh.farm.main.dto.FeedRecipeDto
import me.kshulzh.farm.main.dto.FillStorageDto
import me.kshulzh.farm.main.dto.IngredientTypeDto
import me.kshulzh.farm.main.dto.ProduceFeedDto
import me.kshulzh.farm.main.exception.FeedException
import me.kshulzh.farm.main.exception.FeedProduceException
import me.kshulzh.farm.main.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.main.exception.RecipeNotFoundException
import me.kshulzh.farm.main.mappers.FeedMapper
import me.kshulzh.farm.main.mappers.FeedRecipeMapper
import me.kshulzh.farm.main.mappers.IngredientMapper
import me.kshulzh.farm.main.mappers.IngredientypeMapper
import me.kshulzh.farm.main.mappers.fromWeigth
import me.kshulzh.farm.main.mappers.toWeight
import me.kshulzh.farm.main.repository.FeedRecipeRepository
import me.kshulzh.farm.main.repository.FeedRepository
import me.kshulzh.farm.main.repository.IngredientRepository
import me.kshulzh.farm.main.repository.IngredientSumRepository
import me.kshulzh.farm.main.repository.IngredientTypeRepository
import me.kshulzh.farm.main.repository.searchById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FeedServiceImpl : me.kshulzh.farm.main.api.FeedService {
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
    lateinit var ingredienTypeMapper: IngredientypeMapper

    @Autowired
    lateinit var ingredientMapper: IngredientMapper

    @Autowired
    lateinit var feedRecipeMapper: FeedRecipeMapper

    @Autowired
    lateinit var feedMapper: FeedMapper


    override fun addRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        val recipe = feedRecipeMapper.toEntity(feedRecipe)
        val savedRecipe = feedRecipeRepository.save(recipe)
        ingredientTypeRepository.save(IngredientType().apply {
            id = recipe.ingredientId
            name = recipe.name
        })
        return feedRecipeMapper.toDto(savedRecipe)
    }

    override fun editRecipe(feedRecipe: FeedRecipeDto): FeedRecipeDto {
        return feedRecipeMapper.toDto(feedRecipeRepository.save(feedRecipeMapper.toEntity(feedRecipe)))
    }

    override fun deleteRecipe(feedRecipeId: String) {
        feedRecipeRepository.searchById(feedRecipeId) ?: throw RecipeNotFoundException(
            feedRecipeId
        )
        feedRecipeRepository.deleteById(feedRecipeId)
    }

    override fun getRecipe(feedRecipeId: String): FeedRecipeDto {
        return feedRecipeMapper.toDto(
            feedRecipeRepository.searchById(feedRecipeId) ?: throw RecipeNotFoundException(
                feedRecipeId
            )
        )
    }

    override fun getRecipes(criteria: Map<String, String>?): List<FeedRecipeDto> {
        TODO("Not yet implemented")
    }

    override fun addIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        return ingredienTypeMapper.toDto(ingredientTypeRepository.save(ingredienTypeMapper.toEntity(ingredient)))
    }

    override fun editIngredient(ingredient: IngredientTypeDto): IngredientTypeDto {
        return ingredienTypeMapper.toDto(ingredientTypeRepository.save(ingredienTypeMapper.toEntity(ingredient)))
    }

    override fun deleteIngredient(ingredientId: String) {
        ingredientTypeRepository.searchById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
        ingredientTypeRepository.deleteById(ingredientId)
    }

    override fun getIngredient(ingredientId: String): IngredientTypeDto {
        return ingredienTypeMapper.toDto(
            ingredientTypeRepository.searchById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
        )
    }

    override fun getIngredients(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO("Not yet implemented")
    }

    override fun fillStorage(fillStorageDto: FillStorageDto) {
        val ingredient = ingredientMapper.toEntity(fillStorageDto)
        ingredientRepository.save(ingredient)
        ingredientSumRepository.save(
            ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredient.ingredientType.id)?.apply {
                leftovers = toWeight(fromWeigth(leftovers) + fromWeigth(ingredient.count))
            } ?: IngredientSum().apply {
                id = id()
                ingredientType = ingredient.ingredientType
                leftovers = ingredient.count
            }
        )
    }

    override fun getLeftovers(ingredientId: String): List<FillStorageDto> {
        ingredientTypeRepository.searchById(ingredientId) ?: throw IngredientTypeNotFoundException(ingredientId)
        return ingredientRepository.findIngredientsByIngredientTypeId(ingredientId).map { ingredient ->
            ingredientMapper.toDto(ingredient)
        }
    }

    override fun getLeftovers(criteria: Map<String, String>?): List<IngredientTypeDto> {
        TODO("Not yet implemented")
    }

    override fun produce(produceFeedDto: ProduceFeedDto) {
        val receipt = feedRecipeRepository.searchById(produceFeedDto.receiptId) ?: throw RecipeNotFoundException(
            produceFeedDto.receiptId
        )
        //todo fix if receipt.ingredients == null
        val leftovers = receipt.ingredients!!.map {
            val ingredientSum =
                ingredientSumRepository.findIngredientSumByIngredientTypeId(it.key.id) ?: IngredientSum().apply {
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
            ingredientSumRepository.findIngredientSumByIngredientTypeId(feed.ingredientType.id)
                ?: IngredientSum().apply {
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