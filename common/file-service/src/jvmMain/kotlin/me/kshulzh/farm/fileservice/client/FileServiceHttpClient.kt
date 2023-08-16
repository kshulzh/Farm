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

package me.kshulzh.farm.fileservice.client

import me.kshulzh.farm.common.dto.FileDto
import me.kshulzh.farm.common.http.HttpClient
import me.kshulzh.farm.common.io.PlatformFile
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

actual class FileServiceHttpClient actual constructor(val httpClient: HttpClient) {
    companion object {
        const val PREFIX = "/files/"
    }

    actual suspend fun uploadFile(file: PlatformFile, path: String): FileDto {
        return httpClient.post(file, PREFIX + path, FileDto::class)
    }

    actual suspend fun getFiles(path: String): Array<FileDto> {
        return httpClient.get(PREFIX + path, kClass = (typeOf<Array<FileDto>>().classifier!! as KClass<*>))
    }

    actual suspend fun downloadFile(path: String): PlatformFile {
        return httpClient.get(PREFIX + path, PlatformFile::class)
    }
}