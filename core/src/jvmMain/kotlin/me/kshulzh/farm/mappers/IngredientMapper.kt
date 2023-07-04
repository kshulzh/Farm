package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.FillStorageDto
import me.kshulzh.farm.entity.Ingredient
import me.kshulzh.farm.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.IngredientTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IngredientMapper : MapperEntityDto<Ingredient, FillStorageDto> {
    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    override fun toEntity(dto: FillStorageDto): Ingredient {
        return Ingredient().apply {
            id = dto.id ?: id()
            ingredientType = ingredientTypeRepository.findById(dto.ingredientId)
                ?: throw IngredientTypeNotFoundException(dto.ingredientId)
            count = dto.weght
        }
    }

    override fun toDto(entity: Ingredient): FillStorageDto {
        return FillStorageDto().apply {
            id = entity.id
            ingredientId = entity.ingredientType.id
            weght = entity.count
        }
    }
}