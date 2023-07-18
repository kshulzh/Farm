package me.kshulzh.farm.services

import me.kshulzh.farm.repository.AnimalRepository
import me.kshulzh.farm.repository.AnimalSpeciesRepository
import me.kshulzh.farm.repository.AnimalsInSectionRepository
import me.kshulzh.farm.repository.FeedRecipeRepository
import me.kshulzh.farm.repository.FeedRepository
import me.kshulzh.farm.repository.IngredientRepository
import me.kshulzh.farm.repository.IngredientSumRepository
import me.kshulzh.farm.repository.IngredientTypeRepository
import me.kshulzh.farm.repository.ItemRepository
import me.kshulzh.farm.repository.SectionRepository
import org.springframework.boot.test.mock.mockito.MockBean

open class BasicTest {
    @MockBean
    lateinit var animalRepository: AnimalRepository

    @MockBean
    lateinit var animalSpeciesRepository: AnimalSpeciesRepository

    @MockBean
    lateinit var animalsInSectionRepository: AnimalsInSectionRepository

    @MockBean
    lateinit var sectionRepository: SectionRepository

    @MockBean
    lateinit var ingredientRepository: IngredientRepository

    @MockBean
    lateinit var ingredientTypeRepository: IngredientTypeRepository

    @MockBean
    lateinit var feedRecipeRepository: FeedRecipeRepository

    @MockBean
    lateinit var ingredientSumRepository: IngredientSumRepository

    @MockBean
    lateinit var feedRepository: FeedRepository

    @MockBean
    lateinit var itemRepository: ItemRepository
}