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

package me.kshulzh.farm.mappers

import me.kshulzh.farm.dto.ItemDto
import me.kshulzh.farm.entity.Item
import me.kshulzh.farm.id
import org.springframework.stereotype.Component

@Component
class ItemMapper : MapperEntityDto<Item, ItemDto> {
    override fun toEntity(dto: ItemDto): Item {
        return Item().apply {
            id = dto.id ?: id()
            name = dto.name
            description = dto.description
            price = dto.price
            itemCode = dto.itemCode
            spentMoney = dto.spentMoney
        }
    }

    override fun toDto(entity: Item): ItemDto {
        return ItemDto().apply {
            id = entity.id
            name = entity.name
            description = entity.description
            price = entity.price
            itemCode = entity.itemCode
            spentMoney = entity.spentMoney
        }
    }
}