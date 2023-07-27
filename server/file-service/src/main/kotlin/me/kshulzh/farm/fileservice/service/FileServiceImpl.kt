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

package me.kshulzh.farm.fileservice.service

import me.kshulzh.farm.common.dto.FileDto
import me.kshulzh.farm.common.io.mkDir
import me.kshulzh.farm.fileservice.exception.FileNotFoundException
import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.nio.file.Path

@Service
class FileServiceImpl {
    @Value("\${file-service.folder:files/}")
    lateinit var folder: String
    fun saveFile(path: String, file: MultipartFile): FileDto {
        mkDir(folder + path)
        file.transferTo(Path.of(folder + path + file.originalFilename))
        return FileDto().apply {
            this.path = path
            name = file.originalFilename
            type = file.contentType
            size = file.size
            directory = false
        }
    }

    fun getFiles(path: String): List<FileDto> {
        val tika = Tika()
        if (!File(folder + path).exists()) throw FileNotFoundException(path)
        return File(folder + path).listFiles()?.map {
            FileDto().apply {
                name = it.name
                this.path = path
                size = it.length()
                directory = it.isDirectory
                if (!directory!!) {
                    type = tika.detect(it)
                }
            }
        } ?: listOf(FileDto().apply {
            name = path
            this.path = path
            size = File(folder + path).length()
            directory = false
            type = tika.detect(File(folder + path))

        })
    }

    fun openFile(path: String) =
        if (File(folder + path).exists()) FileInputStream(folder + path) else throw FileNotFoundException(path)
}