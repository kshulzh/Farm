package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.IngredientSum

interface IngredientSumRepository : EntityRepository<IngredientSum> {
    fun findIngredientSumByTypeId(ingredientTypeId: String): IngredientSum?
}