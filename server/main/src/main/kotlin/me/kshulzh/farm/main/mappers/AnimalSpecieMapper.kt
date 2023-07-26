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

import me.kshulzh.farm.main.entity.AnimalSpecie
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.AnimalSpeciesDto
import org.springframework.stereotype.Component

@Component
class AnimalSpecieMapper : MapperEntityDto<AnimalSpecie, AnimalSpeciesDto> {
    override fun toEntity(dto: AnimalSpeciesDto): AnimalSpecie {
        return AnimalSpecie().apply {
            id = dto.id ?: id()
            description = dto.description
            animalType = dto.animalType
        }
    }

    override fun toDto(entity: AnimalSpecie): AnimalSpeciesDto {
        return AnimalSpeciesDto().apply {
            id = entity.id
            description = entity.description
            animalType = entity.animalType
        }
    }
}