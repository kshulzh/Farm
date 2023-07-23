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

import kotlin.reflect.KClass

interface HttpClient {
    companion object {
        fun of(url: String) = HttpClientImpl(url)
    }

    suspend fun <T> request(
        method: Method,
        body: Any? = null,
        path: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ): T

    suspend fun <T> get(path: String, kClass: KClass<*> = Unit::class, headers: Map<String, String> = mutableMapOf()) =
        request<T>(Method.GET, null, path, kClass, headers)

    suspend fun <T> post(
        body: Any? = null,
        path: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = request<T>(Method.POST, body, path, kClass, headers)

    suspend fun <T> put(
        body: Any? = null,
        path: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = request<T>(Method.PUT, body, path, kClass, headers)

    suspend fun <T> delete(
        body: Any? = null,
        path: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = request<T>(Method.DELETE, body, path, kClass, headers)

    suspend fun <T> getById(
        path: String,
        id: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = get<T>("$path/$id", kClass, headers)

    suspend fun <T> putById(
        body: Any? = null,
        path: String,
        id: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = put<T>(body, "$path/$id", kClass, headers)

    suspend fun <T> deleteById(
        body: Any? = null,
        path: String,
        id: String,
        kClass: KClass<*> = Unit::class,
        headers: Map<String, String> = mutableMapOf()
    ) = delete<T>(body, "$path/$id", kClass, headers)
}