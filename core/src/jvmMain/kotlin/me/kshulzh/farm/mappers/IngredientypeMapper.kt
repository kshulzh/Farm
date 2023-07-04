package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.IngredientTypeDto
import me.kshulzh.farm.entity.IngredientType
import me.kshulzh.farm.id
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class IngredientypeMapper : MapperEntityDto<IngredientType, IngredientTypeDto> {
    override fun toEntity(dto: IngredientTypeDto): IngredientType {
        return IngredientType().apply {
            id = dto.id ?: id()
            type = dto.type
            price = dto.price
            description = dto.description
            date = mapStringToDate(dto.date) ?: LocalDateTime.now()
            name = dto.name
            barcode = dto.barcode
        }
    }

    override fun toDto(entity: IngredientType): IngredientTypeDto {
        return IngredientTypeDto().apply {
            id = entity.id
            type = entity.type
            price = entity.price
            description = entity.description
            date = mapDateToString(entity.date)
            name = entity.name
            barcode = entity.barcode
        }
    }
}