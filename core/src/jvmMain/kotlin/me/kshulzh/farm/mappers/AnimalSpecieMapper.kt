package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.AnimalSpeciesDto
import me.kshulzh.farm.entity.AnimalSpecie
import me.kshulzh.farm.id
import org.springframework.stereotype.Component

@Component
class AnimalSpecieMapper : MapperEntityDto<AnimalSpecie, AnimalSpeciesDto> {
    override fun toEntity(dto: AnimalSpeciesDto): AnimalSpecie {
        return AnimalSpecie().apply {
            id = dto.id ?: id()
            description = dto.description
            animalType = dto.animalType
        }
    }

    override fun toDto(entity: AnimalSpecie): AnimalSpeciesDto {
        return AnimalSpeciesDto().apply {
            id = entity.id
            description = entity.description
            animalType = entity.animalType
        }
    }
}