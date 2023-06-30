package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.IngredientType
import org.springframework.stereotype.Repository

@Repository
interface IngredientTypeRepository : EntityRepository<IngredientType> {
}