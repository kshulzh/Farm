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

package me.kshulzh.farm.main.controller

import me.kshulzh.farm.main.api.SectionService
import me.kshulzh.farm.main.client.SectionServiceHttpClient.Companion.COLLECT
import me.kshulzh.farm.main.client.SectionServiceHttpClient.Companion.SECTIONS
import me.kshulzh.farm.main.client.SectionServiceHttpClient.Companion.SECTION_SERVICE_URL
import me.kshulzh.farm.main.dto.SectionCollectDto
import me.kshulzh.farm.main.dto.SectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(SECTION_SERVICE_URL)
@RestController
class SectionController : SectionService {
    @Autowired
    lateinit var sectionService: SectionService

    @PostMapping(SECTIONS)
    override fun addSection(@RequestBody sectionDto: SectionDto): SectionDto {
        return sectionService.addSection(sectionDto)
    }

    @DeleteMapping(SECTIONS)
    override fun deleteSection(@PathVariable("id") id: String) {
        sectionService.deleteSection(id)
    }

    @PutMapping(SECTIONS)
    override fun editSection(@RequestBody sectionDto: SectionDto): SectionDto {
        return sectionService.editSection(sectionDto)
    }

    @GetMapping(SECTIONS)
    override fun getAllSections(): List<SectionDto> {
        return sectionService.getAllSections()
    }

    @PostMapping(COLLECT)
    override fun collect(@RequestBody collectDto: SectionCollectDto) {
        return sectionService.collect(collectDto)
    }
}