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
import me.kshulzh.farm.main.entity.Item
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.ItemDto
import me.kshulzh.farm.main.exception.ItemNotFoundException
import me.kshulzh.farm.main.services.ItemServiceImpl
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
class ItemServiceImplTest : BasicTest() {
    val itemDtos = listOf(
        ItemDto().apply {
            id = id()
            name = "Incubator"
        }
    )

    @Autowired
    lateinit var itemServiceImpl: ItemServiceImpl

    val itemArgumentCaptor = ArgumentCaptor.forClass(Item::class.java)

    @Test
    fun shouldSaveItem() {
        Mockito.`when`(itemRepository.save(any())).thenReturn(Item().apply {
            id = itemDtos[0].id!!
            name = itemDtos[0].name
        })
        itemServiceImpl.addItem(itemDtos[0])
        verify(itemRepository, times(1)).save(capture(itemArgumentCaptor))
        var saved = itemArgumentCaptor.value
        assertEquals(itemDtos[0].id, saved.id)
        assertEquals(itemDtos[0].name, saved.name)
    }

    @Test
    fun shouldRemoveItem() {
        Mockito.`when`(itemRepository.findById(itemDtos[0].id!!)).thenReturn(Optional.of(Item()))
        itemServiceImpl.deleteItem(itemDtos[0].id!!)
        verify(itemRepository).deleteById(itemDtos[0].id!!)
    }

    @Test
    fun shouldNotRemoveItemWhenItenDoesNotExist() {
        Mockito.`when`(itemRepository.deleteById(any())).then { }
        assertThrows<ItemNotFoundException> { itemServiceImpl.deleteItem(itemDtos[0].id!!) }
        verify(itemRepository, never()).deleteById(itemDtos[0].id!!)
    }

    @Test
    fun shouldNotFindItemWhenItemDoesNotExist() {
        Mockito.`when`(itemRepository.findById(any())).thenReturn(Optional.empty())
        assertThrows<ItemNotFoundException> { itemServiceImpl.getItemById(itemDtos[0].id!!) }
    }
}