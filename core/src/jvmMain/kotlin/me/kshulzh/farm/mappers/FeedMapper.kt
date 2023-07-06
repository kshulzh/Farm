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

import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.entity.Feed
import me.kshulzh.farm.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.exception.SectionNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.IngredientTypeRepository
import me.kshulzh.farm.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class FeedMapper : MapperEntityDto<Feed, FeedDto> {
    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    override fun toEntity(dto: FeedDto): Feed {
        return Feed().apply {
            id = dto.id ?: id()
            count = dto.count
            section = sectionRepository.findById(dto.sectionId) ?: throw SectionNotFoundException(dto.sectionId)
            time = mapStringToDate(dto.time) ?: LocalDateTime.now()
            ingredientType = ingredientTypeRepository.findById(dto.ingredientTypeId)
                ?: throw IngredientTypeNotFoundException(dto.ingredientTypeId)
        }
    }

    override fun toDto(entity: Feed): FeedDto {
        return FeedDto().apply {
            id = entity.id
            count = entity.count
            sectionId = entity.section.id
            time = mapDateToString(entity.time)!!
            ingredientTypeId = entity.ingredientType.id
        }
    }
}