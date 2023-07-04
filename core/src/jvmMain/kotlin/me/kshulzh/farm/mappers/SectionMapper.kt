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