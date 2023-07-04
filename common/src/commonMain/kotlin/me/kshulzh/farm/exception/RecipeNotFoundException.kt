package me.kshulzh.farm.exception

class RecipeNotFoundException(val id_: String) : NotFoundException("Recipe", id_)