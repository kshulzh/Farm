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

package me.kshulzh.farm.http

import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.KClass

actual class HttpClientImpl actual constructor(var url: String) : HttpClient {
    val mapper = Gson()
    override suspend fun <T> request(
        method: Method,
        body: Any?,
        path: String,
        kClass: KClass<*>,
        headers: Map<String, String>
    ): T {
        val connection: HttpURLConnection = URL("$url$path").openConnection() as HttpURLConnection
        connection.setRequestProperty("content-type", "application/json")
        headers.forEach {
            connection.setRequestProperty(it.key, it.value)
        }
        connection.requestMethod = method.name
        if (body != null) {
            connection.doOutput = true
            connection.outputStream.write(mapper.toJson(body).toByteArray())
        }
        connection.connect()
        if (connection.responseCode > 399) {
            //todo
            throw RuntimeException()
        }
        if (connection.responseCode != 200) {
            return Unit as T
        }

        return mapper.fromJson(
            InputStreamReader(connection.inputStream), kClass.java
        ) as T
    }
}