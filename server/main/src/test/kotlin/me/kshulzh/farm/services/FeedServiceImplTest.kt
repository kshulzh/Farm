package me.kshulzh.farm.services

import me.kshulzh.farm.Configuration
import me.kshulzh.farm.main.entity.FeedRecipe
import me.kshulzh.farm.main.entity.Ingredient
import me.kshulzh.farm.main.entity.IngredientSum
import me.kshulzh.farm.main.entity.IngredientType
import me.kshulzh.farm.main.entity.Section
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.FeedDto
import me.kshulzh.farm.main.dto.FeedRecipeDto
import me.kshulzh.farm.main.dto.FillStorageDto
import me.kshulzh.farm.main.dto.IngredientTypeDto
import me.kshulzh.farm.main.dto.ProduceFeedDto
import me.kshulzh.farm.main.exception.FeedException
import me.kshulzh.farm.main.exception.FeedProduceException
import me.kshulzh.farm.main.exception.IngredientTypeNotFoundException
import me.kshulzh.farm.main.exception.RecipeNotFoundException
import me.kshulzh.farm.main.mappers.FeedRecipeMapper
import me.kshulzh.farm.main.mappers.IngredientMapper
import me.kshulzh.farm.main.mappers.IngredientypeMapper
import me.kshulzh.farm.main.services.FeedServiceImpl
import me.kshulzh.farm.main.mappers.fromWeigth
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.capture
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.AnnotationConfigContextLoader
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    loader = AnnotationConfigContextLoader::class,
    classes = [Configuration::class]
)
class FeedServiceImplTest : BasicTest() {
    var ingredientTypeDtos = arrayOf(
        IngredientTypeDto().apply {
            id = id()
            name = "Millet"
            price = 10.0
        }, IngredientTypeDto().apply {
            id = id()
            name = "Barley"
            price = 30.0
        }
    )

    val ingredientDtos = arrayOf(
        FillStorageDto().apply {
            id = id()
            ingredientId = ingredientTypeDtos[0].id!!
            weght = "20.0"
        }, FillStorageDto().apply {
            id = id()
            ingredientId = ingredientTypeDtos[1].id!!
            weght = "10.0"
        }
    )
    val feedRecipeDtos = arrayOf(
        FeedRecipeDto().apply {
            id = id()
            ingredientId = id()
            name = "Super Food"
            ingredients = mutableMapOf(
                ingredientTypeDtos[0].id!! to 0.15,
                ingredientTypeDtos[1].id!! to 0.85
            )
        }
    )

    var feedDtos = arrayOf(
        FeedDto().apply {
            count = "10.0"
            ingredientTypeId = ingredientTypeDtos[0].id!!
            this.sectionId = id()
        }
    )

    @Autowired
    lateinit var ingredientypeMapper: IngredientypeMapper

    @Autowired
    lateinit var ingredientMapper: IngredientMapper

    @Autowired
    lateinit var feedRecipeMapper: FeedRecipeMapper

    @Autowired
    lateinit var feedServiceImpl: FeedServiceImpl

    var ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient::class.java)
    var ingredientSumArgumentCaptor = ArgumentCaptor.forClass(IngredientSum::class.java)
    var ingredientTypeArgumentCaptor = ArgumentCaptor.forClass(IngredientType::class.java)
    var recipeArgumentCaptor = ArgumentCaptor.forClass(FeedRecipe::class.java)

    @Test
    fun shouldSaveIngredientType() {
        Mockito.`when`(ingredientTypeRepository.save(any())).thenReturn(IngredientType().apply {
            id = ingredientTypeDtos[0].id!!
            date = LocalDateTime.now()
        })
        feedServiceImpl.addIngredient(ingredientTypeDtos[0])

        verify(ingredientTypeRepository).save(capture(ingredientTypeArgumentCaptor))

        val entity = ingredientTypeArgumentCaptor.value
        assertEquals(ingredientTypeDtos[0].id, entity.id)
        assertEquals(ingredientTypeDtos[0].name, entity.name)
    }

    @Test
    fun shouldEditIngredientType() {
        Mockito.`when`(ingredientTypeRepository.save(any())).thenReturn(IngredientType().apply {
            id = ingredientTypeDtos[0].id!!
            date = LocalDateTime.now()
        })
        feedServiceImpl.editIngredient(ingredientTypeDtos[0])

        verify(ingredientTypeRepository).save(capture(ingredientTypeArgumentCaptor))

        val entity = ingredientTypeArgumentCaptor.value
        assertEquals(ingredientTypeDtos[0].id, entity.id)
        assertEquals(ingredientTypeDtos[0].name, entity.name)
    }

    @Test
    fun shouldDeleteIngredientType() {
        Mockito.`when`(ingredientTypeRepository.findById(any())).thenReturn(Optional.of(IngredientType()))
        Mockito.`when`(ingredientTypeRepository.deleteById(ingredientTypeDtos[0].id!!)).then { }
        feedServiceImpl.deleteIngredient(ingredientTypeDtos[0].id!!)

        verify(ingredientTypeRepository).deleteById(ingredientTypeDtos[0].id!!)
    }

    @Test
    fun shouldNotDeleteWhenIngredientTypeDoesNotExist() {
        Mockito.`when`(ingredientTypeRepository.findById(any())).thenReturn(Optional.empty())
        assertThrows<IngredientTypeNotFoundException> { feedServiceImpl.deleteIngredient(id()) }
    }

    @Test
    fun shouldNotFindWhenIngredientTypeDoesNotExist() {
        Mockito.`when`(ingredientTypeRepository.findById(any())).thenReturn(Optional.empty())
        assertThrows<IngredientTypeNotFoundException> { feedServiceImpl.getIngredient(id()) }
    }

    @Test
    fun shouldFillStorageIfIngredientDoesNotExistInStorage() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientDtos[0].ingredientId))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[0].ingredientId))
            .thenReturn(null)

        feedServiceImpl.fillStorage(ingredientDtos[0])

        verify(ingredientSumRepository).save(capture(ingredientSumArgumentCaptor))
        verify(ingredientRepository).save(capture(ingredientArgumentCaptor))
        val ingredientSum = ingredientSumArgumentCaptor.value
        val ingredient = ingredientArgumentCaptor.value
        assertEquals(ingredientDtos[0].ingredientId, ingredientSum.ingredientType.id)
        assertEquals(ingredientDtos[0].ingredientId, ingredient.ingredientType.id)
        assertEquals(ingredientDtos[0].weght, ingredientSum.leftovers)
        assertEquals(ingredientDtos[0].weght, ingredient.count)
    }

    @Test
    fun shouldFillStorageIfIngredientExistInStorage() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientDtos[1].ingredientId))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[1])))
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[1].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "20.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[1])
            })

        feedServiceImpl.fillStorage(ingredientDtos[1])

        verify(ingredientSumRepository).save(capture(ingredientSumArgumentCaptor))
        verify(ingredientRepository).save(capture(ingredientArgumentCaptor))
        val ingredientSum = ingredientSumArgumentCaptor.value
        val ingredient = ingredientArgumentCaptor.value
        assertEquals(ingredientDtos[1].ingredientId, ingredientSum.ingredientType.id)
        assertEquals(ingredientDtos[1].ingredientId, ingredient.ingredientType.id)
        assertEquals("30.0", ingredientSum.leftovers)
        assertEquals(ingredientDtos[1].weght, ingredient.count)
    }

    @Test
    fun shouldSaveRecipe() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[1].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[1])))
        Mockito.`when`(feedRecipeRepository.save(any())).thenReturn(FeedRecipe().apply {
            id = feedRecipeDtos[0].id!!
            ingredientId = feedRecipeDtos[0].ingredientId!!
            this.ingredients = mapOf(
                IngredientType().apply {
                    id = ingredientTypeDtos[0].id!!
                } to 0.15, IngredientType().apply {
                    id = ingredientTypeDtos[1].id!!
                } to 0.85)
        })
        feedServiceImpl.addRecipe(feedRecipeDtos[0])

        verify(feedRecipeRepository).save(capture(recipeArgumentCaptor))

        val entity = recipeArgumentCaptor.value
        val keys = entity.ingredients!!.keys
        assertEquals(2, entity.ingredients!!.size)
        assertEquals(0.15, entity.ingredients!!.get(keys.first()))
    }

    @Test
    fun shouldEditRecipe() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[1].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[1])))
        Mockito.`when`(feedRecipeRepository.save(any())).thenReturn(FeedRecipe().apply {
            id = feedRecipeDtos[0].id!!
            ingredientId = feedRecipeDtos[0].ingredientId!!
            this.ingredients = mapOf(
                IngredientType().apply {
                    id = ingredientTypeDtos[0].id!!
                } to 0.15, IngredientType().apply {
                    id = ingredientTypeDtos[1].id!!
                } to 0.85)
        })
        feedServiceImpl.addRecipe(feedRecipeDtos[0])

        verify(feedRecipeRepository).save(capture(recipeArgumentCaptor))

        val entity = recipeArgumentCaptor.value
        val keys = entity.ingredients!!.keys
        assertEquals(2, entity.ingredients!!.size)
        assertEquals(0.15, entity.ingredients!!.get(keys.first()))
    }

    @Test
    fun shouldDeleteRecipe() {
        Mockito.`when`(feedRecipeRepository.findById(any())).thenReturn(Optional.of(FeedRecipe()))
        Mockito.`when`(feedRecipeRepository.deleteById(feedRecipeDtos[0].id!!)).then { }
        feedServiceImpl.deleteRecipe(feedRecipeDtos[0].id!!)

        verify(feedRecipeRepository).deleteById(feedRecipeDtos[0].id!!)
    }

    @Test
    fun shouldNotDeleteWhenRecipeDoesNotExist() {
        Mockito.`when`(feedRecipeRepository.findById(any())).thenReturn(Optional.empty())
        assertThrows<RecipeNotFoundException> { feedServiceImpl.deleteRecipe(id()) }
    }

    @Test
    fun shouldNotFindWhenRecipeDoesNotExist() {
        assertThrows<RecipeNotFoundException> { feedServiceImpl.getRecipe(id()) }
    }

    @Test
    fun shouldProduce() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[1].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[1])))
        Mockito.`when`(ingredientTypeRepository.findById(feedRecipeDtos[0].ingredientId!!))
            .thenReturn(Optional.of(IngredientType().apply {
                id = feedRecipeDtos[0].ingredientId!!
                name = feedRecipeDtos[0].name!!
            }))

        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[0].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "20.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[0])
            })
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[1].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "20.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[1])
            })
        Mockito.`when`(feedRecipeRepository.findById(feedRecipeDtos[0].id!!)).thenReturn(
            Optional.of(FeedRecipe().apply {
                id = feedRecipeDtos[0].id!!
                ingredientId = feedRecipeDtos[0].ingredientId!!
                name = "Super Food"
                ingredients = mutableMapOf(
                    ingredientypeMapper.toEntity(ingredientTypeDtos[0]) to 0.15,
                    ingredientypeMapper.toEntity(ingredientTypeDtos[1]) to 0.85
                )
            })
        )

        feedServiceImpl.produce(ProduceFeedDto().apply {
            count = "10.0"
            receiptId = feedRecipeDtos[0].id!!
        })

        verify(ingredientSumRepository, times(3)).save(capture(ingredientSumArgumentCaptor))
        val values = ingredientSumArgumentCaptor.allValues
        val out = values[0]
        val first = values[1]
        val second = values[2]
        assertEquals(10.0, fromWeigth(out.leftovers))
        assertEquals(18.5, fromWeigth(first.leftovers))
        assertEquals(11.5, fromWeigth(second.leftovers))
    }

    @Test
    fun shouldNotProduce() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[1].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[1])))
        Mockito.`when`(ingredientTypeRepository.findById(feedRecipeDtos[0].ingredientId!!))
            .thenReturn(Optional.of(IngredientType().apply {
                id = feedRecipeDtos[0].ingredientId!!
                name = feedRecipeDtos[0].name!!
            }))

        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[0].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "10.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[0])
            })
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[1].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "10.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[1])
            })
        Mockito.`when`(feedRecipeRepository.findById(feedRecipeDtos[0].id!!)).thenReturn(Optional.of(
            FeedRecipe().apply {
                id = feedRecipeDtos[0].id!!
                ingredientId = feedRecipeDtos[0].ingredientId!!
                name = "Super Food"
                ingredients = mutableMapOf(
                    ingredientypeMapper.toEntity(ingredientTypeDtos[0]) to 0.15,
                    ingredientypeMapper.toEntity(ingredientTypeDtos[1]) to 0.85
                )
            }
        ))

        assertThrows<FeedProduceException> {
            feedServiceImpl.produce(ProduceFeedDto().apply {
                count = "20.0"
                receiptId = feedRecipeDtos[0].id!!
            })
        }

        verify(ingredientSumRepository, never()).save(any())
    }

    @Test
    fun shouldFeed() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[0].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "10.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[0])
            })
        Mockito.`when`(sectionRepository.findById(any())).thenReturn(Optional.of(Section()))

        feedServiceImpl.feed(feedDtos[0])

        verify(ingredientSumRepository).save(capture(ingredientSumArgumentCaptor))
        verify(feedRepository).save(any())
        val left = ingredientSumArgumentCaptor.value
        assertEquals(0.0, fromWeigth(left.leftovers))
    }

    @Test
    fun shouldNotFeed() {
        Mockito.`when`(ingredientTypeRepository.findById(ingredientTypeDtos[0].id!!))
            .thenReturn(Optional.of(ingredientypeMapper.toEntity(ingredientTypeDtos[0])))
        Mockito.`when`(ingredientSumRepository.findIngredientSumByIngredientTypeId(ingredientDtos[0].ingredientId))
            .thenReturn(IngredientSum().apply {
                id = id()
                leftovers = "5.0"
                ingredientType = ingredientypeMapper.toEntity(ingredientTypeDtos[0])
            })
        Mockito.`when`(sectionRepository.findById(any())).thenReturn(Optional.of(Section()))

        assertThrows<FeedException> { feedServiceImpl.feed(feedDtos[0]) }

        verify(ingredientSumRepository, never()).save(any())
        verify(feedRepository, never()).save(any())
    }
}