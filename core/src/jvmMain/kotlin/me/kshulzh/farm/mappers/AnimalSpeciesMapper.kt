package me.kshulzh.farm.mappers

import me.kshulzh.farm.entity.AnimalSpecies
import me.kshulzh.farm.dto.AnimalSpeciesDto
import org.springframework.stereotype.Component

@Component
class AnimalSpeciesMapper : MapperEntityDto<AnimalSpecies, AnimalSpeciesDto> {
    override fun toEntity(dto: AnimalSpeciesDto): AnimalSpecies {
        return AnimalSpecies().apply {
            id = dto.id
            description = dto.description
            animalType = dto.animalType
        }
    }

    override fun toDto(entity: AnimalSpecies): AnimalSpeciesDto {
        return AnimalSpeciesDto().apply {
            id = entity.id
            description =entity.description
            animalType = entity.animalType
        }
    }
}