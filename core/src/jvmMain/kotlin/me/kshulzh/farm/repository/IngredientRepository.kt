package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Ingredient
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository : EntityRepository<Ingredient> {
    fun findIngredientsByTypeId(ingredientTypeId: String): List<Ingredient>
}