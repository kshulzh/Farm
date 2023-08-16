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

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.*

@SuppressLint("StaticFieldLeak")
lateinit var activity: Activity
actual fun writeToFile(file: String): OutputStream {
    val parts = file.split("/").dropLastWhile {
        it.contains(".")
    }
    var part = ""
    parts.forEach {
        part += it
        File(activity.applicationContext.filesDir, part).mkdir()
        part += File.separator
    }
    val f = File(activity.applicationContext.filesDir, file)
    return FileOutputStream(f)
}

actual fun readFromFile(file: String): InputStream {
    val f = File(activity.applicationContext.filesDir, file)
    return FileInputStream(f)
}

actual fun readFromFileByUri(uri: String): InputStream {
    return activity.contentResolver.openInputStream(Uri.parse(uri))!!
}

actual fun readBitmap(file: String): ImageBitmap {
    return BitmapFactory.decodeStream(readFromFile(file)).asImageBitmap()
}