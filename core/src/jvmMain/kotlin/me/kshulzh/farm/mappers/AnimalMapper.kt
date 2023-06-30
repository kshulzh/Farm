package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import me.kshulzh.farm.entity.Animal
import me.kshulzh.farm.entity.AnimalSpecies
import me.kshulzh.farm.exception.AnimalSpecieNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.AnimalSpeciesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AnimalMapper : MapperEntityDto<Animal, AnimalDto> {
    @Autowired
    lateinit var animalSpeciesMapper :AnimalSpeciesMapper
    @Autowired
    lateinit var animalSpeciesRepository: AnimalSpeciesRepository
    override fun toEntity(dto: AnimalDto): Animal {
        return Animal().apply {
            id = dto.id
            type = dto.type
            gender = dto.gender
            specie = dto.specieId?.let { animalSpeciesRepository.findById(it) ?: throw AnimalSpecieNotFoundException(id) }
            weight = dto.weight
        }
    }

    override fun toDto(entity: Animal): AnimalDto {
        return AnimalDto().apply {
            id = entity.id
            type = entity.type
            gender = entity.gender
            specieId = entity.specie?.id
            weight = entity.weight
        }
    }
}