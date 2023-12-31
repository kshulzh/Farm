package me.kshulzh.farm.services

import me.kshulzh.farm.Configuration
import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.dto.AnimalInSectionDto
import me.kshulzh.farm.dto.AnimalSpeciesDto
import me.kshulzh.farm.dto.SectionDto
import me.kshulzh.farm.entity.Animal
import me.kshulzh.farm.entity.AnimalType
import me.kshulzh.farm.entity.AnimalsInSection
import me.kshulzh.farm.entity.Gender
import me.kshulzh.farm.entity.Section
import me.kshulzh.farm.exception.NotFoundException
import me.kshulzh.farm.id
import me.kshulzh.farm.repository.AnimalRepository
import me.kshulzh.farm.repository.AnimalSpeciesRepository
import me.kshulzh.farm.repository.AnimalsInSectionRepository
import me.kshulzh.farm.repository.SectionRepository
import me.kshulzh.farm.utils.capture
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.AnnotationConfigContextLoader
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    loader = AnnotationConfigContextLoader::class,
    classes = [Configuration::class]
)
class AnimalServiceImplTest : BasicTest() {
    val animalSpeciesDtos = listOf(AnimalSpeciesDto().apply {
        id = id()
        animalType = AnimalType.QUAIL
        description = "This is a quail"
    })

    val animalDtos = listOf(
        AnimalDto().apply {
            id = id()
            gender = Gender.NONE
            weight = null
            specieId = null
            type = null
            state = null
        },
        AnimalDto().apply {
            id = id()
            gender = Gender.NONE
            weight = null
            specieId = animalSpeciesDtos[0].id
            type = AnimalType.QUAIL
            state = null
        },
        AnimalDto().apply {
            id = id()
            gender = Gender.NONE
            weight = null
            specieId = id()
            type = AnimalType.QUAIL
            state = null
        }
    )

    val sectionDtos = listOf(SectionDto().apply {
        id = id()
    })
    val animalInSectionDtos = listOf(AnimalInSectionDto().apply {
        animalId = animalDtos[0].id!!
        to = sectionDtos[0].id
        startDate = null
        endDate = null
    }, AnimalInSectionDto().apply {
        animalId = animalDtos[1].id!!
        to = id()
        startDate = null
        endDate = null
    })

    @Autowired
    lateinit var animalServiceImpl: AnimalService

    val animalArgumentCaptor = ArgumentCaptor.forClass(Animal::class.java)
    val animalInSectionArgumentCaptor = ArgumentCaptor.forClass(AnimalsInSection::class.java)

    @Test
    fun shouldSaveAnimal() {
        Mockito.`when`(animalRepository.save(any())).thenReturn(Animal())
        animalServiceImpl.addAnimal(animalDtos[0])
        verify(animalRepository, times(1)).save(capture(animalArgumentCaptor))
        var saved = animalArgumentCaptor.value
        assertEquals(animalDtos[0].id, saved.id)
    }

    @Test
    fun shouldNotSaveAnimalWhenAnimalSpecieDoesNotExist() {
        Mockito.`when`(animalSpeciesRepository.findById(any())).thenReturn(null)
        assertThrows<NotFoundException> { animalServiceImpl.addAnimal(animalDtos[2]) }
        verify(animalRepository, never()).save(any())
    }

    @Test
    fun shouldRemoveAnimal() {
        Mockito.`when`(animalRepository.deleteById(any())).thenReturn(Animal())
        animalServiceImpl.deleteAnimal(animalDtos[1].id!!)
        verify(animalRepository).deleteById(animalDtos[1].id!!)
    }

    @Test
    fun shouldNotRemoveAnimalWhenAnimalDoesNotExist() {
        Mockito.`when`(animalRepository.deleteById(any())).thenReturn(null)
        assertThrows<NotFoundException> { animalServiceImpl.deleteAnimal(animalDtos[1].id!!) }
        verify(animalRepository).deleteById(animalDtos[1].id!!)
    }

    @Test
    fun shouldMoveFromTo() {
        Mockito.`when`(sectionRepository.findById(sectionDtos[0].id!!)).thenReturn(Section())
        Mockito.`when`(animalsInSectionRepository.getSectionIdByAnimalId(animalDtos[0].id!!))
            .thenReturn(AnimalsInSection().apply {
                animal = Animal().apply {
                    id = animalDtos[0].id!!
                }
                section = Section().apply {
                    id = sectionDtos[0].id!!
                }
                startDate = LocalDateTime.of(2022, 2, 2, 1, 1)
            })
        Mockito.`when`(animalsInSectionRepository.save(any())).thenReturn(AnimalsInSection())
        Mockito.`when`(animalRepository.findById(animalDtos[0].id!!)).thenReturn(Animal().apply {
            id = animalDtos[0].id!!
        })

        animalServiceImpl.move(animalInSectionDtos[0])

        verify(animalsInSectionRepository, times(2)).save(capture(animalInSectionArgumentCaptor))
        val from = animalInSectionArgumentCaptor.allValues[0]
        val to = animalInSectionArgumentCaptor.allValues[1]
        assertTrue(from.endDate!!.compareTo(to.startDate) <= 0)
        assertEquals(from.animal.id, to.animal.id)
    }
}