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

import me.kshulzh.farm.api.ItemService
import me.kshulzh.farm.dto.ItemDto
import me.kshulzh.farm.exception.ItemNotFoundException
import me.kshulzh.farm.mappers.ItemMapper
import me.kshulzh.farm.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl : ItemService {
    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var itemMapper: ItemMapper
    override fun addItem(item: ItemDto): ItemDto {
        return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(item)))
    }

    override fun editItem(item: ItemDto): ItemDto {
        return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(item)))
    }

    override fun getItemById(id: String): ItemDto {
        return itemMapper.toDto(itemRepository.findById(id) ?: throw ItemNotFoundException(id))
    }

    override fun getItems(): List<ItemDto> {
        return itemRepository.getAll().map {
            itemMapper.toDto(it)
        }
    }

    override fun deleteItem(id: String) {
        itemRepository.findById(id) ?: throw ItemNotFoundException(id)
        itemRepository.deleteById(id)
    }
}