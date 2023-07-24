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

package me.kshulzh.farm.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import me.kshulzh.farm.ui.common.config.fontSize

@Composable
fun PropertyComponent(name: String, value: MutableState<String?>, editMode: Boolean) {
    Row {
        Text(
            name, Modifier
                .weight(0.2f), fontSize = fontSize
        )
        if (editMode) {
            BasicTextField(
                value.value ?: "", {
                    value.value = it.ifBlank { null }
                }, Modifier
                    .weight(0.2f)
                    .background(Color(0xf8f8f8ff)),
                textStyle = TextStyle(fontSize = fontSize)
            )
        } else {
            Text(
                value.value ?: "", Modifier
                    .weight(0.2f), fontSize = fontSize
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}