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

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.reflect.KClass

actual class HttpClientImpl actual constructor(var url: String) : HttpClient {
    val mapper = ObjectMapper()
    override suspend fun <T> request(
        method: Method,
        body: Any?,
        path: String,
        kClass: KClass<*>,
        headers: Map<String, String>
    ): T {
        val responce = java.net.http.HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder()
                    .header("content-type", "application/json")
                    .method(method.name, HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                    .uri(URI("$url$path")).build(), HttpResponse.BodyHandlers.ofString()
            )
        if (responce.statusCode()>399) {
            //todo
            throw RuntimeException()
        }
        if (responce.body().isBlank()) {
            return Unit as T
        }

        return mapper.readValue(
            responce.body(), kClass.java
        ) as T
    }
}