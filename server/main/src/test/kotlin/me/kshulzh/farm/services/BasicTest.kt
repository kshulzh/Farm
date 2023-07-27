package me.kshulzh.farm.services

import me.kshulzh.farm.main.repository.AnimalRepository
import me.kshulzh.farm.main.repository.AnimalSpeciesRepository
import me.kshulzh.farm.main.repository.AnimalsInSectionRepository
import me.kshulzh.farm.main.repository.FeedRecipeRepository
import me.kshulzh.farm.main.repository.FeedRepository
import me.kshulzh.farm.main.repository.IngredientRepository
import me.kshulzh.farm.main.repository.IngredientSumRepository
import me.kshulzh.farm.main.repository.IngredientTypeRepository
import me.kshulzh.farm.main.repository.ItemRepository
import me.kshulzh.farm.main.repository.SectionRepository
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