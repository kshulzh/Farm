package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.FeedDto
import me.kshulzh.farm.entity.Feed
import me.kshulzh.farm.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.exception.SectionNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.IngredientTypeRepository
import me.kshulzh.farm.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class FeedMapper : MapperEntityDto<Feed, FeedDto> {
    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    override fun toEntity(dto: FeedDto): Feed {
        return Feed().apply {
            id = dto.id ?: id()
            count = dto.count
            section = sectionRepository.findById(dto.sectionId) ?: throw SectionNotFoundException(dto.sectionId)
            time = mapStringToDate(dto.time) ?: LocalDateTime.now()
            ingredientType = ingredientTypeRepository.findById(dto.ingredientTypeId)
                ?: throw IngredientTypeNotFoundException(dto.ingredientTypeId)
        }
    }

    override fun toDto(entity: Feed): FeedDto {
        return FeedDto().apply {
            id = entity.id
            count = entity.count
            sectionId = entity.section.id
            time = mapDateToString(entity.time)!!
            ingredientTypeId = entity.ingredientType.id
        }
    }
}