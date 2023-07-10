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

package me.kshulzh.farm.client

import me.kshulzh.farm.dto.ItemDto
import me.kshulzh.farm.http.HttpClient

class ItemServiceHttpClient(val httpClient: HttpClient) {
    companion object {
        val ITEMS_SERVICE_URL = "/items-service"
        val ITEMS = "/items"
        val ITEMS_SERVICE_ITEMS_URL = "$ITEMS_SERVICE_URL$ITEMS"
    }

    suspend fun addItem(item: ItemDto): ItemDto {
        return httpClient.post(item, ITEMS_SERVICE_ITEMS_URL, ItemDto::class)
    }

    suspend fun editItem(item: ItemDto): ItemDto {
        return httpClient.put(item, ITEMS_SERVICE_ITEMS_URL, ItemDto::class)
    }

    suspend fun getItemById(id: String): ItemDto {
        return httpClient.getById(ITEMS_SERVICE_ITEMS_URL, id, ItemDto::class)
    }

    suspend fun getItems(): Array<ItemDto> {
        return httpClient.get(ITEMS_SERVICE_ITEMS_URL, kClass = Array<ItemDto>::class)
    }

    suspend fun deleteItem(id: String) {
        return httpClient.deleteById(path = ITEMS_SERVICE_ITEMS_URL, id = id)
    }
}