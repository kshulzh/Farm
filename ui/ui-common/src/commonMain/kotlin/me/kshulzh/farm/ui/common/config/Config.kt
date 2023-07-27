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

package me.kshulzh.farm.ui.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.asCoroutineDispatcher
import me.kshulzh.farm.main.client.ItemServiceHttpClient
import me.kshulzh.farm.common.http.HttpClient
import me.kshulzh.farm.ui.common.io.readFromFile
import me.kshulzh.farm.ui.common.io.writeToFile
import me.kshulzh.farm.ui.common.lang.EnPhrases
import me.kshulzh.farm.ui.common.lang.Phrases
import me.kshulzh.farm.ui.common.lang.Phrases.Companion.LANGS
import java.util.concurrent.Executors

var config = loadConfig()

class Config {
    var url: String? = null
        set(value) {
            itemServiceHttpClient = ItemServiceHttpClient(HttpClient.of(value!!))
            field = value
        }
    var lang: String = "en"
        set(value) {
            phrases = LANGS[value]!!
            field = value
        }
}

var phrases: Phrases = EnPhrases
var itemServiceHttpClient: ItemServiceHttpClient = ItemServiceHttpClient(HttpClient.of(config.url ?: ""))
var coroutine = Executors.newCachedThreadPool().asCoroutineDispatcher()
const val CONFIG_FILE = "config.json"
fun saveConfig() {
    val mapper = ObjectMapper()
    val fileOutputStream = writeToFile(CONFIG_FILE)
    fileOutputStream.write(mapper.writeValueAsString(config).toByteArray())
    fileOutputStream.close()
}

fun loadConfig(): Config {
    return try {
        val mapper = ObjectMapper()
        mapper.readValue(readFromFile(CONFIG_FILE), Config::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        Config()
    }
}