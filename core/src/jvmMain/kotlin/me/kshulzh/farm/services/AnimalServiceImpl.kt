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

import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import me.kshulzh.farm.exception.AnimalNotFoundException
import me.kshulzh.farm.exception.AnimalSpecieNotFoundException
import me.kshulzh.farm.exception.SectionNotFoundException
import me.kshulzh.farm.mappers.AnimalInSectionMapper
import me.kshulzh.farm.mappers.AnimalMapper
import me.kshulzh.farm.mappers.AnimalSpecieMapper
import me.kshulzh.farm.mappers.mapStringToDate
import me.kshulzh.farm.repository.AnimalRepository
import me.kshulzh.farm.repository.AnimalSpeciesRepository
import me.kshulzh.farm.repository.AnimalsInSectionRepository
import me.kshulzh.farm.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnimalServiceImpl : AnimalService {
    @Autowired
    lateinit var animalRepository: AnimalRepository

    @Autowired
    lateinit var animalsInSectionRepository: AnimalsInSectionRepository

    @Autowired
    lateinit var animalSpeciesRepository: AnimalSpeciesRepository

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var animalMapper: AnimalMapper

    @Autowired
    lateinit var animalSpecieMapper: AnimalSpecieMapper

    @Autowired
    lateinit var animalInSectionMapper: AnimalInSectionMapper

    override fun addAnimal(animal: AnimalDto) {
        if (animal.specieId != null) {
            animalSpeciesRepository.findById(animal.specieId!!)
                ?: throw AnimalSpecieNotFoundException(animal.specieId!!)
        }
        animalRepository.save(animalMapper.toEntity(animal))
    }

    override fun deleteAnimal(id: String) {
        animalRepository.deleteById(id) ?: throw AnimalNotFoundException(id)
    }

    override fun move(animalInSectionDto: AnimalInSectionDto) {
        var animalInSection = animalsInSectionRepository.getSectionIdByAnimalId(animalInSectionDto.animalId)
        if (animalInSection != null) {
            if (animalInSection.section?.id == animalInSectionDto.to)
                animalInSection.endDate = mapStringToDate(animalInSectionDto.startDate) ?: LocalDateTime.now()
            animalsInSectionRepository.save(animalInSection)
        }

        if (animalInSectionDto.to != null) {
            sectionRepository.findById(animalInSectionDto.to!!)
                ?: throw SectionNotFoundException(animalInSectionDto.to!!)
            animalsInSectionRepository.save(
                animalInSectionMapper.toEntity(animalInSectionDto)
            )
        }
    }

    override fun getAnimalById(id: String): AnimalDto {
        return animalMapper.toDto(animalRepository.findById(id) ?: throw AnimalNotFoundException(id))
    }

    override fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        return animalSpecieMapper.toDto(animalSpeciesRepository.save(animalSpecieMapper.toEntity(animalSpecieDto)))
    }

    override fun getAllAnimals(): List<AnimalDto> {
        return animalRepository.getAll().map { animalMapper.toDto(it) }
    }

    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto> {
        return animalSpeciesRepository.getAll().map { animalSpecieMapper.toDto(it) }
    }
}