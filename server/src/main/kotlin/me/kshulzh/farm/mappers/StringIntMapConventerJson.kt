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

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import me.kshulzh.farm.repository.IngredientTypeRepository
import org.springframework.beans.factory.annotation.Autowired

@Converter(autoApply = true)
class StringIntMapConventerJson : AttributeConverter<Map<String, Int>, String> {
    @Autowired
    lateinit var ingredientTypeRepository: IngredientTypeRepository
    val mapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, Int>?): String {
        return mapper.writeValueAsString(attribute!!)
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Int> {
        return mapper.readValue(dbData, object : TypeReference<Map<String, Int>>() {})
    }
}
