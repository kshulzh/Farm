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

package me.kshulzh.farm.controller

import me.kshulzh.farm.api.ItemService
import me.kshulzh.farm.api.ItemServiceHTTP
import me.kshulzh.farm.dto.ItemDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController : ItemServiceHTTP {
    @Autowired
    lateinit var itemService: ItemService
    override fun addItem(item: ItemDto): ItemDto {
        return itemService.addItem(item)
    }

    override fun editItem(item: ItemDto): ItemDto {
        return itemService.editItem(item)
    }

    override fun getItemById(id: String): ItemDto {
        return itemService.getItemById(id)
    }

    override fun deleteItem(id: String) {
        itemService.deleteItem(id)
    }

    override fun getItems(): List<ItemDto> {
        return itemService.getItems()
    }
}