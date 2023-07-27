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

import me.kshulzh.farm.main.api.AnimalService
import me.kshulzh.farm.main.dto.AnimalDto
import me.kshulzh.farm.main.dto.AnimalInSectionDto
import me.kshulzh.farm.main.dto.AnimalSpeciesDto
import me.kshulzh.farm.main.exception.AnimalNotFoundException
import me.kshulzh.farm.main.exception.AnimalSpecieNotFoundException
import me.kshulzh.farm.main.exception.SectionNotFoundException
import me.kshulzh.farm.main.mappers.AnimalInSectionMapper
import me.kshulzh.farm.main.mappers.AnimalMapper
import me.kshulzh.farm.main.mappers.AnimalSpecieMapper
import me.kshulzh.farm.main.mappers.mapStringToDate
import me.kshulzh.farm.main.repository.AnimalRepository
import me.kshulzh.farm.main.repository.AnimalSpeciesRepository
import me.kshulzh.farm.main.repository.AnimalsInSectionRepository
import me.kshulzh.farm.main.repository.SectionRepository
import me.kshulzh.farm.main.repository.searchById
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

    override fun addAnimal(animal: AnimalDto): AnimalDto {
        if (animal.specieId != null) {
            animalSpeciesRepository.searchById(animal.specieId!!)
                ?: throw AnimalSpecieNotFoundException(animal.specieId!!)
        }
        return animalMapper.toDto(animalRepository.save(animalMapper.toEntity(animal)))
    }

    override fun deleteAnimal(id: String) {
        animalRepository.searchById(id) ?: throw AnimalNotFoundException(id)
        animalRepository.deleteById(id)
    }

    override fun move(animalInSectionDto: AnimalInSectionDto) {
        var animalInSection = animalsInSectionRepository.getSectionIdByAnimalId(animalInSectionDto.animalId)
        if (animalInSection != null) {
            if (animalInSection.section?.id == animalInSectionDto.to)
                animalInSection.endDate = mapStringToDate(animalInSectionDto.startDate) ?: LocalDateTime.now()
            animalsInSectionRepository.save(animalInSection)
        }

        if (animalInSectionDto.to != null) {
            sectionRepository.searchById(animalInSectionDto.to!!)
                ?: throw SectionNotFoundException(animalInSectionDto.to!!)
            animalsInSectionRepository.save(
                animalInSectionMapper.toEntity(animalInSectionDto)
            )
        }
    }

    override fun getAnimalById(id: String): AnimalDto {
        return animalMapper.toDto(animalRepository.searchById(id) ?: throw AnimalNotFoundException(id))
    }

    override fun addAnimalSpecie(animalSpecieDto: AnimalSpeciesDto): AnimalSpeciesDto {
        return animalSpecieMapper.toDto(animalSpeciesRepository.save(animalSpecieMapper.toEntity(animalSpecieDto)))
    }

    override fun getAllAnimals(): List<AnimalDto> {
        return animalRepository.findAll().map { animalMapper.toDto(it) }
    }

    override fun getAllAnimalSpecies(): List<AnimalSpeciesDto> {
        return animalSpeciesRepository.findAll().map { animalSpecieMapper.toDto(it) }
    }
}