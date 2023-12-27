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

package me.kshulzh.farm.ui.common.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.ItemDto
import me.kshulzh.farm.main.dto.SectionDto
import me.kshulzh.farm.ui.common.components.ActionCircularProgressIndicator
import me.kshulzh.farm.ui.common.components.MapPropertyComponent
import me.kshulzh.farm.ui.common.components.PropertyComponent
import me.kshulzh.farm.ui.common.config.coroutine
import me.kshulzh.farm.ui.common.config.fontSize
import me.kshulzh.farm.ui.common.config.itemServiceHttpClient
import me.kshulzh.farm.ui.common.config.phrases
import me.kshulzh.farm.ui.common.config.sectionServiceHttpClient

object SectionsApp {
    lateinit var selectedSection: MutableState<SectionDto?>
    lateinit var sections: MutableList<SectionDto>
    lateinit var editMode: MutableState<Boolean>
    lateinit var loading: MutableState<Boolean>
    suspend fun fetchSections() {
        CoroutineScope(coroutine).launch {
            loading.value = true
            sections.clear()
            sections.addAll(sectionServiceHttpClient.getAllSections().asList().toMutableList())
            loading.value = false
        }
    }
    @Composable
    fun SectionsApp() {
        if (loading.value) {
            ActionCircularProgressIndicator {
                fetchSections()
            }
        } else {
            if(selectedSection.value == null) {
                SectionsAppViewAll()
            } else {

            }
        }
    }
    @Composable
    fun SectionsAppView(sectionDto: SectionDto, edit: Boolean = false) {
        //var width: Double? = null
        //var height: Double? = null
        //    var depth: Double? = null
        val id = remember { mutableStateOf(sectionDto.id) }
        val name = remember { mutableStateOf(sectionDto.name) }
        val number = remember { mutableStateOf<String?>(sectionDto.number.toString()) }
        val capacity = remember { mutableStateMapOf(*(sectionDto.capacity.map { it.key to it.value?.toString() }.toTypedArray()))}
        val width = remember { mutableStateOf(sectionDto.width?.toString()) }
        val height = remember { mutableStateOf(sectionDto.height?.toString()) }
        val depth = remember { mutableStateOf(sectionDto.depth?.toString()) }
        Column {
            PropertyComponent(phrases.ID, id, edit)
            PropertyComponent(phrases.NAME, name, edit)
            PropertyComponent(phrases.NUMBER, number, edit)
            MapPropertyComponent(phrases.CAPACITY, capacity, edit)
            PropertyComponent(phrases.WIDTH, width, edit)
            PropertyComponent(phrases.HEIGHT, height, edit)
            PropertyComponent(phrases.DEPTH, depth, edit)

            if (edit) {
                Row {
                    Button({
                        with(sectionDto) {
                            this.name = name.value
                            this.number = number.value?.toLong()
                            this.capacity = capacity.toMap().mapValues { it.value?.toInt() ?: -1 }
                            this.width = width.value?.toDouble()
                            this.height = height.value?.toDouble()
                            this.depth = depth.value?.toDouble()

                        }
                        if (sectionDto.id == null) {
                            sectionDto.id = id()
                            sections.add(sectionDto)
                            CoroutineScope(coroutine).launch {
                                sectionServiceHttpClient.addSection(sectionDto)
                            }.start()
                        } else {
                            CoroutineScope(coroutine).launch {
                                sectionServiceHttpClient.editSection(sectionDto)
                            }.start()
                        }
                        editMode.value = false
                        selectedSection.value = null
                    }) {
                        Text(phrases.SAVE)
                    }
                    Button({
                        editMode.value = false
                        selectedSection.value = null
                    }) {
                        Text(phrases.CANCEL)
                    }
                }
            }
        }
    }

    @Composable
    fun SectionsAppViewAll() {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            sections.forEach {
                SectionsAppViewRow(it)
            }

        }
        Column(
            modifier = Modifier.fillMaxWidth(0.97f).fillMaxHeight(0.97f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            IconButton({
                selectedSection.value = SectionDto()
                editMode.value = true
            }) {
                Icon(Icons.Filled.Add, phrases.NEW)
            }
        }

    }

    @Composable
    fun SectionsAppViewRow(sectionDto: SectionDto) {
        var isDeleted by remember { mutableStateOf(false) }
        if (!isDeleted) {
            Row {
                Button({
                    selectedSection.value = sectionDto
                    editMode.value = true
                }) {
                    Text(sectionDto.name ?: sectionDto.number.toString() ?: sectionDto.id ?: phrases.UNNAMED, fontSize = fontSize)
                }
                IconButton({
                    CoroutineScope(coroutine).launch {
                        sectionServiceHttpClient.deleteSection(sectionDto.id!!)
                        sections.remove(sectionDto)
                        isDeleted = true
                    }.start()
                }) {
                    Icon(Icons.Filled.Delete, phrases.DELETE)
                }
            }
        }
    }
}