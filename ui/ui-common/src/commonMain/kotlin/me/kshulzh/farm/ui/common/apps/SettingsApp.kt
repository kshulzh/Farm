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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import me.kshulzh.farm.ui.common.components.PropertyComponent
import me.kshulzh.farm.ui.common.config.config
import me.kshulzh.farm.ui.common.config.phrases
import me.kshulzh.farm.ui.common.config.saveConfig

@Composable
fun SettingsApp() {
    Column {
        val url = remember { mutableStateOf<String?>(config.url) }
        PropertyComponent("URL", url, true)
        Row {
            Button({
                config.url = url.value
                saveConfig()
            }) {
                Text(phrases.SAVE)
            }
        }
    }
}