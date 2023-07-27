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

@file:OptIn(ExperimentalFoundationApi::class)

package me.kshulzh.farm.ui.common.apps

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kshulzh.farm.common.id
import me.kshulzh.farm.main.dto.ItemDto
import me.kshulzh.farm.ui.common.components.PropertyComponent
import me.kshulzh.farm.ui.common.config.coroutine
import me.kshulzh.farm.ui.common.config.fontSize
import me.kshulzh.farm.ui.common.config.itemServiceHttpClient
import me.kshulzh.farm.ui.common.config.phrases

lateinit var selectedItem: MutableState<ItemDto?>
lateinit var editMode: MutableState<Boolean>
lateinit var items: MutableList<ItemDto>
lateinit var loading: MutableState<Boolean>

suspend fun fetchItems() {
    CoroutineScope(coroutine).launch {
        loading.value = true
        items.clear()
        items.addAll(itemServiceHttpClient.getItems().asList().toMutableList())
        loading.value = false
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsApp() {
    selectedItem = remember { mutableStateOf(null) }
    editMode = remember { mutableStateOf(false) }
    loading = remember { mutableStateOf(true) }
    items = remember { mutableListOf() }

    if (loading.value) {
        LaunchedEffect(Unit) {
            fetchItems()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

    } else {
        if (selectedItem.value == null) {
            ItemsAppViewAll()
        } else {
            ItemsAppView(selectedItem.value!!, editMode.value)
        }
    }
}

@Composable
fun ItemsAppView(itemDto: ItemDto, edit: Boolean = false) {
    val id = remember { mutableStateOf(itemDto.id) }
    val name = remember { mutableStateOf(itemDto.name) }
    val description = remember { mutableStateOf(itemDto.description) }
    val price = remember { mutableStateOf(itemDto.price?.toString()) }
    val itemCode = remember { mutableStateOf(itemDto.itemCode) }
    val spentMoney = remember { mutableStateOf(itemDto.spentMoney?.toString()) }
    Column {
        PropertyComponent("id", id, edit)
        PropertyComponent(phrases.NAME, name, edit)
        PropertyComponent(phrases.DESCRIPTION, description, edit)
        PropertyComponent(phrases.PRICE, price, edit)
        PropertyComponent(phrases.ITEM_CODE, itemCode, edit)
        PropertyComponent(phrases.SPENT_MONEY, spentMoney, edit)
        if (edit) {
            Row {
                Button({
                    with(itemDto) {
                        this.name = name.value
                        this.description = description.value
                        this.itemCode = itemCode.value
                        this.price = price.value?.toDouble()
                        this.spentMoney = spentMoney.value?.toDouble()
                    }
                    if (itemDto.id == null) {
                        itemDto.id = id()
                        items.add(itemDto)
                        CoroutineScope(coroutine).launch {
                            itemServiceHttpClient.addItem(itemDto)
                        }.start()
                    } else {
                        CoroutineScope(coroutine).launch {
                            itemServiceHttpClient.editItem(itemDto)
                        }.start()
                    }
                    editMode.value = false
                    selectedItem.value = null
                }) {
                    Text(phrases.SAVE)
                }
                Button({
                    editMode.value = false
                    selectedItem.value = null
                }) {
                    Text(phrases.CANCEL)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ItemsAppViewAll() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEach {
            ItemsAppViewRow(it)
        }

    }
    Column(
        modifier = Modifier.fillMaxWidth(0.97f).fillMaxHeight(0.97f),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        IconButton({
            selectedItem.value = ItemDto()
            editMode.value = true
        }) {
            Icon(Icons.Filled.Add, phrases.NEW)
        }
    }
}

@Composable
fun ItemsAppViewRow(itemDto: ItemDto) {
    var isDeleted by remember { mutableStateOf(false) }
    if (!isDeleted) {
        Row {
            Button({
                selectedItem.value = itemDto
                editMode.value = true
            }) {
                Text(itemDto.name ?: itemDto.id ?: phrases.UNNAMED, fontSize = fontSize)
            }
            IconButton({
                CoroutineScope(coroutine).launch {
                    itemServiceHttpClient.deleteItem(itemDto.id!!)
                    items.remove(itemDto)
                    isDeleted = true
                }.start()
            }) {
                Icon(Icons.Filled.Delete, phrases.DELETE)
            }
        }
    }
}