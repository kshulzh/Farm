package me.kshulzh.farm.api

import me.kshulzh.farm.dto.SectionCollectDto
import me.kshulzh.farm.dto.SectionDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/section-service")
interface SectionServiceHTTP : SectionService {
    @PostMapping("/sections")
    override fun addSection(@RequestBody sectionDto: SectionDto)
    @PostMapping("/collect")
    override fun collect(@RequestBody collectDto: SectionCollectDto)
}