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

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.kshulzh.farm.common.http.HttpClientImpl
import me.kshulzh.farm.ui.common.App
import me.kshulzh.farm.ui.common.io.readFromFile
import me.kshulzh.farm.ui.common.io.writeToFile


fun main() = application {
    HttpClientImpl.readFromFile = ::readFromFile
    HttpClientImpl.writeToFile = ::writeToFile
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
