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

import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.entity.AnimalsInSection
import me.kshulzh.farm.exception.AnimalNotFoundException
import me.kshulzh.farm.exception.SectionNotFoundException
import me.kshulzh.farm.repository.AnimalRepository
import me.kshulzh.farm.repository.SectionRepository
import me.kshulzh.farm.repository.searchById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AnimalInSectionMapper : MapperEntityDto<AnimalsInSection, AnimalInSectionDto> {
    @Autowired
    lateinit var sectionMapper: SectionMapper

    @Autowired
    lateinit var animalRepository: AnimalRepository

    @Autowired
    lateinit var sectionRepository: SectionRepository

    override fun toEntity(dto: AnimalInSectionDto): AnimalsInSection {
        return AnimalsInSection().apply {
            startDate = mapStringToDate(dto.startDate) ?: LocalDateTime.now()
            endDate = mapStringToDate(dto.endDate) ?: LocalDateTime.now()
            section = dto.to?.let { sectionRepository.searchById(it) ?: throw SectionNotFoundException(it) }
            animal = animalRepository.searchById(dto.animalId) ?: throw AnimalNotFoundException(dto.animalId)
        }
    }

    override fun toDto(entity: AnimalsInSection): AnimalInSectionDto {
        return AnimalInSectionDto().apply {
            animalId = entity.animal.id
            startDate = mapDateToString(entity.startDate)
            endDate = mapDateToString(entity.endDate)
            to = entity.section?.id
        }
    }
}