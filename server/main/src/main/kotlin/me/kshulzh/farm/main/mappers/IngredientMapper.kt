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

package me.kshulzh.farm.main.mappers

import me.kshulzh.farm.main.entity.Ingredient
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.FillStorageDto
import me.kshulzh.farm.main.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.main.repository.IngredientTypeRepository
import me.kshulzh.farm.main.repository.searchById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IngredientMapper : MapperEntityDto<Ingredient, FillStorageDto> {
    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    override fun toEntity(dto: FillStorageDto): Ingredient {
        return Ingredient().apply {
            id = dto.id ?: id()
            ingredientType = ingredientTypeRepository.searchById(dto.ingredientId)
                ?: throw IngredientTypeNotFoundException(dto.ingredientId)
            count = dto.weght
        }
    }

    override fun toDto(entity: Ingredient): FillStorageDto {
        return FillStorageDto().apply {
            id = entity.id
            ingredientId = entity.ingredientType.id
            weght = entity.count
        }
    }
}