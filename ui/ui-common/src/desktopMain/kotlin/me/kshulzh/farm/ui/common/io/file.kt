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

package me.kshulzh.farm.ui.common.io

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import me.kshulzh.farm.common.io.mkDir
import org.jetbrains.skiko.toBitmap
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URI
import javax.imageio.ImageIO

actual fun writeToFile(file: String): OutputStream {
    mkDir(file)
    return FileOutputStream(file)
}

actual fun readFromFile(file: String): InputStream {
    return FileInputStream(file)
}

actual fun readBitmap(file: String): ImageBitmap {
    val input = ImageIO.read(readFromFile(file))
    return input.toBitmap().asComposeImageBitmap()
}

actual fun readFromFileByUri(uri: String): InputStream {
    println(uri)
    return FileInputStream(File(URI(uri)))
}