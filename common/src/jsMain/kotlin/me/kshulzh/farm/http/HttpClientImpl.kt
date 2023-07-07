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

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise
import kotlin.js.json

actual class HttpClientImpl actual constructor(var url:String) : HttpClient {
    override suspend fun <T> request(method: Method, body: Any?, path: String, headers: Map<String, String>): T {
        var res:Response? = null
        try {
            val res =
                window.fetch(
                    input = "$url$path",
                    init = RequestInit(
                        body = body,
                        method = method.name,
                        headers = Headers().apply {
                            append("content-type" , "application/json")
                            headers.forEach {
                                append(it.key,it.value)
                            }
                            //append("Access-Control-Allow-Origin", url)
                        }.asDynamic()
                    )
                ).await()
            return if (res.ok) res.json().await().unsafeCast<T>() else throw RuntimeException("${res.status}")
        } catch (e:Throwable) {
            e.printStackTrace()
            throw RuntimeException(e.message)
        }


    }
}