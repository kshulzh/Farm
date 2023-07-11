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

import me.kshulzh.farm.dto.SectionDto
import me.kshulzh.farm.entity.Section
import me.kshulzh.farm.id
import org.springframework.stereotype.Component

@Component
class SectionMapper : MapperEntityDto<Section, SectionDto> {
    override fun toEntity(dto: SectionDto): Section {
        return Section().apply {
            id = dto.id ?: id()
            capacity = dto.capacity
            width = dto.width
            height = dto.height
            depth = dto.depth
        }
    }

    override fun toDto(entity: Section): SectionDto {
        return SectionDto().apply {
            id = entity.id
            capacity = entity.capacity
            width = entity.width
            height = entity.height
            depth = entity.depth
        }
    }
}