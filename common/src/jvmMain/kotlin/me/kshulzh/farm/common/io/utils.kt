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

package me.kshulzh.farm.common.io

import java.io.File
import java.io.InputStream
import java.io.OutputStream

const val SIZE = 8192
fun mkDir(path: String) {
    val parts = path.split("/").dropLastWhile {
        it.contains(".")
    }
    var part = ""
    parts.forEach {
        part += it
        File(part).mkdir()
        part += File.separator
    }
}

fun fileExtension(path: String): String {
    return path.substring(path.lastIndexOf('.') + 1)
}

fun transferTo(inputStream: InputStream, outputStream: OutputStream) {
    val buffer = ByteArray(SIZE)
    var len: Int = inputStream.read(buffer)
    while (len != -1) {
        outputStream.write(buffer, 0, len)
        len = inputStream.read(buffer)
    }
    outputStream.flush()
    inputStream.close()
}