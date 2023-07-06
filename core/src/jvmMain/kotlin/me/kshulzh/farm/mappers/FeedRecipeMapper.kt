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

package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.FeedRecipeDto
import me.kshulzh.farm.entity.FeedRecipe
import me.kshulzh.farm.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.IngredientTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FeedRecipeMapper : MapperEntityDto<FeedRecipe, FeedRecipeDto> {
    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    override fun toEntity(dto: FeedRecipeDto): FeedRecipe {
        return FeedRecipe().apply {
            id = dto.id ?: id()
            ingredientId = dto.ingredientId ?: id()
            name = dto.name
            description = dto.description
            ingredients = dto.ingredients?.mapKeys { entry ->
                ingredientTypeRepository.findById(entry.key) ?: throw IngredientTypeNotFoundException(entry.key)
            }
        }
    }

    override fun toDto(entity: FeedRecipe): FeedRecipeDto {
        return FeedRecipeDto().apply {
            id = entity.id
            name = entity.name
            ingredientId = entity.ingredientId
            description = entity.description
            ingredients = entity.ingredients?.mapKeys { entry -> entry.key.id }
        }
    }
}