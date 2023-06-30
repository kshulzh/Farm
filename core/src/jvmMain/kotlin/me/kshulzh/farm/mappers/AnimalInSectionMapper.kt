package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.SectionDto
import me.kshulzh.farm.entity.AnimalsInSection
import me.kshulzh.farm.exception.AnimalNotFoundException
import me.kshulzh.farm.exception.AnimalSpecieNotFoundException
import me.kshulzh.farm.exception.SectionNotFoundException
import me.kshulzh.farm.repository.AnimalRepository
import me.kshulzh.farm.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
@Component
class AnimalInSectionMapper : MapperEntityDto<AnimalsInSection, AnimalInSectionDto> {
    @Autowired
    lateinit var sectionMapper: SectionMapper
    @Autowired
    lateinit var animalRepository: AnimalRepository
    @Autowired
    lateinit var sectionRepository: SectionRepository

    override fun toEntity(dto: AnimalInSectionDto): AnimalsInSection {
        return AnimalsInSection().apply {
            startDate = mapStringToDate(dto.startDate) ?: LocalDateTime.now()
            endDate = mapStringToDate(dto.endDate) ?: LocalDateTime.now()
            section = dto.to?.let { sectionRepository.findById(it) ?: throw SectionNotFoundException(it) }
            animal = animalRepository.findById(dto.animalId) ?: throw AnimalNotFoundException(dto.animalId)
        }
    }

    override fun toDto(entity: AnimalsInSection): AnimalInSectionDto {
        return AnimalInSectionDto().apply {
            animalId = entity.animal.id
            startDate = mapDateToString(entity.startDate)
            endDate = mapDateToString(entity.endDate)
            to = entity.section?.id
        }
    }
}