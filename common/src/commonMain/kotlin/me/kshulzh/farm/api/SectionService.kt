package me.kshulzh.farm.api

import me.kshulzh.farm.dto.SectionCollectDto
import me.kshulzh.farm.dto.SectionDto

interface SectionService {
    fun addSection(sectionDto: SectionDto)
    fun collect(collectDto: SectionCollectDto)
}