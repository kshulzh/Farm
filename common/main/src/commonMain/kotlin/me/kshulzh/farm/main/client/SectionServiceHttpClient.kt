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

package me.kshulzh.farm.main.client

import me.kshulzh.farm.common.http.HttpClient
import me.kshulzh.farm.main.dto.SectionCollectDto
import me.kshulzh.farm.main.dto.SectionDto
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

class SectionServiceHttpClient(val httpClient: HttpClient) {
    companion object {
        const val SECTION_SERVICE_URL = "/section-service"
        const val SECTIONS = "/sections"
        const val COLLECT = "/collect"
        const val SECTION_SERVICE_SECTIONS = "$SECTION_SERVICE_URL$SECTIONS"
        const val SECTION_SERVICE_COLLECT = "$SECTION_SERVICE_URL$COLLECT"
    }

    suspend fun addSection(sectionDto: SectionDto): SectionDto {
        return httpClient.post(sectionDto, SECTION_SERVICE_SECTIONS, SectionDto::class)
    }

    suspend fun deleteSection(id: String) {
        return httpClient.deleteById(path = SECTION_SERVICE_SECTIONS, id = id)
    }

    suspend fun editSection(sectionDto: SectionDto) {
        return httpClient.put(sectionDto, SECTION_SERVICE_SECTIONS, SectionDto::class)
    }

    suspend fun getAllSections(): Array<SectionDto> {
        return httpClient.get(
            SECTION_SERVICE_SECTIONS,
            (typeOf<Array<SectionDto>>().classifier!! as KClass<*>)
        )
    }

    suspend fun collect(collectDto: SectionCollectDto) {
        httpClient.post<Unit>(collectDto, SECTION_SERVICE_COLLECT, SectionDto::class)
    }
}