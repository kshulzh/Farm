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

import me.kshulzh.farm.main.entity.IngredientType
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.IngredientTypeDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class IngredientypeMapper : MapperEntityDto<IngredientType, IngredientTypeDto> {
    override fun toEntity(dto: IngredientTypeDto): IngredientType {
        return IngredientType().apply {
            id = dto.id ?: id()
            type = dto.type
            price = dto.price
            description = dto.description
            date = mapStringToDate(dto.date) ?: LocalDateTime.now()
            name = dto.name
            barcode = dto.barcode
        }
    }

    override fun toDto(entity: IngredientType): IngredientTypeDto {
        return IngredientTypeDto().apply {
            id = entity.id
            type = entity.type
            price = entity.price
            description = entity.description
            date = mapDateToString(entity.date)
            name = entity.name
            barcode = entity.barcode
        }
    }
}