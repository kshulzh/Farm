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

package me.kshulzh.farm.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.kshulzh.farm.ui.common.apps.ItemsApp
import me.kshulzh.farm.ui.common.apps.SettingsApp
import me.kshulzh.farm.ui.common.config.fontSize
import me.kshulzh.farm.ui.common.config.phrases

enum class Apps {
    SETTINGS,
    ITEMS
}

lateinit var tempApp: MutableState<Apps>

@Composable
fun App() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerElevation = 90.dp,
        topBar = {
            IconButton({
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        },
        drawerGesturesEnabled = true,
        drawerContent = {
            Column(Modifier.width(200.dp)) {
                Button({
                    tempApp.value = Apps.ITEMS
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }, Modifier.fillMaxWidth()) {
                    Row {
                        Icon(Icons.Filled.Build, phrases.ITEMS)
                        Spacer(Modifier.width(10.dp))
                        Text(phrases.ITEMS, fontSize = fontSize)
                    }
                }
                Spacer(Modifier.height(10.dp))
                Button({
                    tempApp.value = Apps.SETTINGS
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }, Modifier.fillMaxWidth()) {
                    Row {
                        Icon(Icons.Filled.Settings, phrases.SETTINGS)
                        Spacer(Modifier.width(10.dp))
                        Text(phrases.SETTINGS, fontSize = fontSize)
                    }
                }
            }
        },

        ) {
        MainApp()
    }
}

@Composable
fun MainApp() {
    tempApp = remember { mutableStateOf(Apps.SETTINGS) }
    when (tempApp.value) {
        Apps.ITEMS -> ItemsApp.ItemsApp()
        Apps.SETTINGS -> SettingsApp()
    }
}


