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

package me.kshulzh.farm.ui.common.components

import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.io.File

data class ChosenFileImpl(
    override val path: String,
    override val file: File,
    override val uri: String

) : ChosenFile

@Composable
actual fun FileChooser(
    show: Boolean,
    path: String?,
    directory: Boolean,
    multiple: Boolean,
    fileExtensions: List<String>,
    onChoose: (List<ChosenFile>?) -> Unit
) {
    val mimeTypeMap = MimeTypeMap.getSingleton()
    val mimeTypes = if (fileExtensions.isNotEmpty()) {
        fileExtensions.mapNotNull { ext ->
            mimeTypeMap.getMimeTypeFromExtension(ext)
        }.toTypedArray()
    } else {
        emptyArray()
    }
    if (directory) {
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocumentTree()) { result ->
                if (result != null) {
                    onChoose(
                        listOf(ChosenFileImpl(result.toString(), File(result.path!!), result.toString()))
                    )
                } else {
                    onChoose(null)
                }
            }
        LaunchedEffect(Unit) {
            if (show) {
                launcher.launch(null)
            }
        }
    } else {
        val launcher = if (multiple) {
            rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
                if (result.isNotEmpty()) {
                    onChoose(result.map {
                        ChosenFileImpl(it.toString(), File(it.path!!), it.toString())
                    })
                } else {
                    onChoose(null)
                }
            }
        } else {
            rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { result ->
                if (result != null) {
                    onChoose(listOf(ChosenFileImpl(result.toString(), File(result.path!!), result.toString())))
                } else {
                    onChoose(null)
                }
            }
        }
        if (show) {
            LaunchedEffect(Unit) {
                launcher.launch(mimeTypes)
            }
        }
    }

}