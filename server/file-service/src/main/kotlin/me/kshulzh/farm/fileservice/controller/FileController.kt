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

package me.kshulzh.farm.fileservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.kshulzh.farm.common.io.transferTo
import me.kshulzh.farm.fileservice.client.FileServiceHttpClient.Companion.PREFIX
import me.kshulzh.farm.fileservice.service.FileServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileController {
    @Autowired
    lateinit var fileServiceImpl: FileServiceImpl

    @PostMapping("$PREFIX**")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        httpServletRequest: HttpServletRequest
    ) {
        val path = httpServletRequest.requestURI.substring(PREFIX.length)
        fileServiceImpl.saveFile(path, file)
    }

    @GetMapping("$PREFIX**")
    fun getFiles(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val mapper = ObjectMapper()
        val path = request.requestURI.substring(PREFIX.length)
        if (path.contains(".")) {
            response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"${path.substring(path.lastIndexOf("/") + 1)}\""
            );
            transferTo(fileServiceImpl.openFile(path), response.outputStream);
        } else {
            mapper.writeValue(response.outputStream, fileServiceImpl.getFiles(path))
        }
        response.flushBuffer()
    }
}