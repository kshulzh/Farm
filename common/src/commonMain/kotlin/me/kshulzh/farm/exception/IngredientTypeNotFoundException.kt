package me.kshulzh.farm.exception

class IngredientTypeNotFoundException(val id_: String) : NotFoundException("Ingredient Type", id_)