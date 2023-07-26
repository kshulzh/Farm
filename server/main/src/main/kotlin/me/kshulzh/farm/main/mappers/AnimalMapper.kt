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

import me.kshulzh.farm.main.entity.Animal
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.AnimalDto
import me.kshulzh.farm.main.entity.Gender
import me.kshulzh.farm.main.exception.AnimalSpecieNotFoundException
import me.kshulzh.farm.main.repository.AnimalSpeciesRepository
import me.kshulzh.farm.main.repository.searchById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AnimalMapper : MapperEntityDto<Animal, AnimalDto> {
    @Autowired
    lateinit var animalSpecieMapper: AnimalSpecieMapper

    @Autowired
    lateinit var animalSpeciesRepository: AnimalSpeciesRepository
    override fun toEntity(dto: AnimalDto): Animal {
        return Animal().apply {
            id = dto.id ?: id()
            type = dto.type
            gender = Gender.valueOf(dto.gender)
            specie =
                dto.specieId?.let { animalSpeciesRepository.searchById(it) ?: throw AnimalSpecieNotFoundException(id) }
            weight = dto.weight
        }
    }

    override fun toDto(entity: Animal): AnimalDto {
        return AnimalDto().apply {
            id = entity.id
            type = entity.type
            gender = entity.gender.name
            specieId = entity.specie?.id
            weight = entity.weight
        }
    }
}