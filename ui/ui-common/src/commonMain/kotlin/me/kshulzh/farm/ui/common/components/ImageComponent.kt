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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kshulzh.farm.common.fileExtension
import me.kshulzh.farm.common.io.PlatformFile
import me.kshulzh.farm.ui.common.config.coroutine
import me.kshulzh.farm.ui.common.config.fileService
import me.kshulzh.farm.ui.common.io.readBitmap
import me.kshulzh.farm.ui.common.io.readFromFileByUri
import me.kshulzh.farm.ui.common.io.writeToFile
import me.kshulzh.farm.ui.common.utils.md5
import java.io.File

@Composable
fun ImagesPropertyComponent(images: MutableList<String?>, onClick: (String?, Int?) -> Unit, remoteFolder: String) {

    var fileChooser by remember { mutableStateOf(false) }
    LazyVerticalGrid(
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(100.dp)
    ) {
        items(images.size) { i ->
            ImagePropertyElement(images[i], {})
        }
        item {
            IconButton({
                fileChooser = true
            }, Modifier.width(100.dp).height(100.dp)) {
                Image(Icons.Rounded.Share, "")
            }
        }
    }
    FileChooser(fileChooser, fileExtensions = listOf("png", "jpg")) {
        if (it != null) {
            it.forEach {
                val input = readFromFileByUri(it.uri)
                val byteArray = ByteArray(input.available())
                input.read(byteArray)
                input.close()
                val localPath = "$remoteFolder${md5(byteArray)}.${fileExtension(it.path)}"
                writeToFile(localPath).apply {
                    write(byteArray)
                    close()
                }
                CoroutineScope(coroutine).launch {
                    fileService.uploadFile(File(localPath) as PlatformFile, remoteFolder)
                }
                images.add(localPath)
            }
        }
        fileChooser = false
    }
}

@Composable
fun ImagePropertyElement(value: String?, onClick: () -> Unit) {
    if (value != null) {
        Box(Modifier.width(100.dp).height(100.dp)) {
            Image(readBitmap(value), "", contentScale = ContentScale.FillBounds)
        }
    }
}
