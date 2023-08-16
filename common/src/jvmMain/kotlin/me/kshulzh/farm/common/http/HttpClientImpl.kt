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

package me.kshulzh.farm.common.http

import com.google.gson.Gson
import me.kshulzh.farm.common.io.transferTo
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import kotlin.reflect.KClass


actual class HttpClientImpl actual constructor(var url: String) : HttpClient {
    val mapper = Gson()

    companion object {
        var writeToFile: (String) -> OutputStream = { FileOutputStream(it) }

        var readFromFile: (String) -> InputStream = { FileInputStream(it) }
        var folderPath = ""
    }

    override suspend fun <T> request(
        method: Method,
        body: Any?,
        path: String,
        kClass: KClass<*>,
        headers: Map<String, String>
    ): T {
        val connection: HttpURLConnection = URL("$url$path").openConnection() as HttpURLConnection
        headers.forEach {
            connection.setRequestProperty(it.key, it.value)
        }
        connection.requestMethod = method.name
        if (body != null) {
            if (body is File) {
                loadFile(body, connection)
            } else {
                connection.setRequestProperty("content-type", "application/json")
                loadJson(body, connection)
            }
        }
        connection.connect()
        if (connection.responseCode > 399) {
            //todo
            throw RuntimeException()
        }
        if (connection.responseCode != 200) {
            @Suppress("UNCHECKED_CAST")
            return Unit as T
        }
        if (connection.getHeaderField("Content-Disposition") != null) {
            val path = folderPath + path.substring("/files/".length)
            val fileOutputStream = writeToFile(path)
            transferTo(connection.inputStream, fileOutputStream)
            @Suppress("UNCHECKED_CAST")
            return File(path) as T
        }

        @Suppress("UNCHECKED_CAST")
        return mapper.fromJson(
            InputStreamReader(connection.inputStream), kClass.java
        ) as T
    }

    fun loadFile(body: File, connection: HttpURLConnection) {
        val boundary = (System.currentTimeMillis()).toULong().toString()
        val CRLF = "\r\n"
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary)
        connection.setRequestProperty("Content-Disposition", "form-data")
        connection.doOutput = true
        val outputStream = connection.outputStream
        val mimeType: String = URLConnection.guessContentTypeFromName(body.name)
        val writer = PrintWriter(OutputStreamWriter(outputStream, "UTF-8"), true)

        writer.append("--" + boundary).append(CRLF)
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"${body.name}\"$CRLF")
        writer.append("Content-Type: $mimeType; charset=UTF-8$CRLF") // Text file itself must be saved in this charset!
        writer.append(CRLF).flush()

        val inputStream = readFromFile(body.absolutePath)
        transferTo(inputStream, outputStream)
        writer.append(CRLF).flush() // CRLF is important! It indicates end of boundary.
        writer.append("--" + boundary + "--").append(CRLF).flush()
    }

    fun loadJson(body: Any, connection: HttpURLConnection) {
        connection.doOutput = true
        connection.outputStream.write(mapper.toJson(body).toByteArray())
    }
}