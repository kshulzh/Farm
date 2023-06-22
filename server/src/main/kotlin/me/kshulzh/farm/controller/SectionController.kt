package me.kshulzh.farm.controller

import me.kshulzh.farm.api.SectionServiceHTTP
import me.kshulzh.farm.dto.SectionCollectDto
import me.kshulzh.farm.dto.SectionDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SectionController : SectionServiceHTTP {
    override fun addSection(sectionDto: SectionDto) {
        TODO()
    }
    override fun collect(collectDto: SectionCollectDto) {
        TODO()
    }
}