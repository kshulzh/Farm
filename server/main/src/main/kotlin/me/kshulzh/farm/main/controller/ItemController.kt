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

package me.kshulzh.farm.main.controller

import me.kshulzh.farm.main.api.ItemService
import me.kshulzh.farm.main.client.ItemServiceHttpClient.Companion.ITEMS
import me.kshulzh.farm.main.client.ItemServiceHttpClient.Companion.ITEMS_SERVICE_URL
import me.kshulzh.farm.main.dto.ItemDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ITEMS_SERVICE_URL)
class ItemController : ItemService {
    @Autowired
    lateinit var itemService: ItemService
    @PostMapping(ITEMS)
    override fun addItem(@RequestBody item: ItemDto): ItemDto {
        return itemService.addItem(item)
    }

    @PutMapping(ITEMS)
    override fun editItem(@RequestBody item: ItemDto): ItemDto {
        return itemService.editItem(item)
    }

    @GetMapping("$ITEMS/{id}")
    override fun getItemById(@PathVariable("id") id: String): ItemDto {
        return itemService.getItemById(id)
    }

    @DeleteMapping("$ITEMS/{id}")
    override fun deleteItem(@PathVariable("id") id: String) {
        itemService.deleteItem(id)
    }

    @GetMapping(ITEMS)
    override fun getItems(): List<ItemDto> {
        return itemService.getItems()
    }
}