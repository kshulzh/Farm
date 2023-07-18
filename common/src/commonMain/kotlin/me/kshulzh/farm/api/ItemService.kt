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

package me.kshulzh.farm.api

import me.kshulzh.farm.dto.ItemDto

interface ItemService {
    fun addItem(item: ItemDto): ItemDto

    fun editItem(item: ItemDto): ItemDto

    fun getItemById(id: String): ItemDto

    fun getItems(): List<ItemDto>

    fun deleteItem(id: String)
}