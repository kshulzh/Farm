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

import me.kshulzh.farm.main.api.SectionService
import me.kshulzh.farm.main.dto.SectionCollectDto
import me.kshulzh.farm.main.dto.SectionDto
import me.kshulzh.farm.main.exception.SectionNotFoundException
import me.kshulzh.farm.main.mappers.SectionMapper
import me.kshulzh.farm.main.repository.SectionRepository
import me.kshulzh.farm.main.repository.searchById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SectionServiceImpl : SectionService {
    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var sectionMapper: SectionMapper
    override fun addSection(sectionDto: SectionDto): SectionDto {
        return sectionMapper.toDto(sectionRepository.save(sectionMapper.toEntity(sectionDto)))
    }

    override fun deleteSection(id: String) {
        //todo remove all animals from section
        sectionRepository.searchById(id) ?: throw SectionNotFoundException(id)
        sectionRepository.deleteById(id)
    }

    override fun editSection(sectionDto: SectionDto): SectionDto {
        sectionRepository.searchById(sectionDto.id!!) ?: throw SectionNotFoundException(sectionDto.id!!)
        return sectionMapper.toDto(sectionRepository.save(sectionMapper.toEntity(sectionDto)))
    }

    override fun getAllSections(): List<SectionDto> {
        return sectionMapper.mapListToDto(sectionRepository.findAll())
    }

    override fun collect(collectDto: SectionCollectDto) {
        TODO("Not yet implemented")
    }
}