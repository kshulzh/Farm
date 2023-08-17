/*
 *   Copyright (c) 2023. Kyrylo Shulzhenko
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package me.kshulzh.farm.services

import me.kshulzh.farm.Configuration
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.SectionDto
import me.kshulzh.farm.main.entity.Section
import me.kshulzh.farm.main.exception.SectionNotFoundException
import me.kshulzh.farm.main.services.SectionServiceImpl
import me.kshulzh.farm.utils.capture
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.AnnotationConfigContextLoader
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    loader = AnnotationConfigContextLoader::class,
    classes = [Configuration::class]
)
class SectionServiceImplTest : BasicTest() {
    val sectionDtos = listOf(
        SectionDto().apply {
            id = id()
            capacity = mapOf("1" to 40)
        }
    )

    @Autowired
    lateinit var sectionServiceImpl: SectionServiceImpl

    val sectionArgumentCaptor = ArgumentCaptor.forClass(Section::class.java)

    @Test
    fun shouldSaveSection() {
        Mockito.`when`(sectionRepository.save(any())).thenReturn(Section().apply {
            id = sectionDtos[0].id!!
            capacity = sectionDtos[0].capacity
        })
        sectionServiceImpl.addSection(sectionDtos[0])
        verify(sectionRepository, times(1)).save(capture(sectionArgumentCaptor))
        var saved = sectionArgumentCaptor.value
        assertEquals(sectionDtos[0].id, saved.id)
        assertEquals(sectionDtos[0].capacity, saved.capacity)
    }

    @Test
    fun shouldRemoveSection() {
        Mockito.`when`(sectionRepository.findById(sectionDtos[0].id!!)).thenReturn(Optional.of(Section()))
        sectionServiceImpl.deleteSection(sectionDtos[0].id!!)
        verify(sectionRepository).deleteById(sectionDtos[0].id!!)
    }

    @Test
    fun shouldNotRemoveSectionWhenItenDoesNotExist() {
        Mockito.`when`(sectionRepository.deleteById(any())).then { }
        assertThrows<SectionNotFoundException> { sectionServiceImpl.deleteSection(sectionDtos[0].id!!) }
        verify(sectionRepository, never()).deleteById(sectionDtos[0].id!!)
    }

    @Test
    fun shouldEditSection() {
        Mockito.`when`(sectionRepository.findById(sectionDtos[0].id!!)).thenReturn(Optional.of(Section()))
        Mockito.`when`(sectionRepository.save(any())).thenReturn(Section().apply {
            id = sectionDtos[0].id!!
            capacity = sectionDtos[0].capacity
        })
        sectionServiceImpl.editSection(sectionDtos[0])
        verify(sectionRepository, times(1)).save(capture(sectionArgumentCaptor))
        var saved = sectionArgumentCaptor.value
        assertEquals(sectionDtos[0].id, saved.id)
        assertEquals(sectionDtos[0].capacity, saved.capacity)
    }
}