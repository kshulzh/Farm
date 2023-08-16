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

package me.kshulzh.farm.common

import kotlin.random.Random
import kotlin.random.nextUBytes

@OptIn(ExperimentalUnsignedTypes::class)
fun id(): String {
    val randomBytes = UByteArray(16)
    Random.nextUBytes(randomBytes)
    randomBytes[6] = (randomBytes[6].toInt() and 0x0f).toUByte() /* clear version        */
    randomBytes[6] = (randomBytes[6].toInt() or 0x40).toUByte() /* set to version 4     */
    randomBytes[8] = (randomBytes[8].toInt() and 0x3f).toUByte() /* clear variant        */
    randomBytes[8] = (randomBytes[8].toInt() or 0x80).toUByte()
    return "${bytesToHexNum(randomBytes, 4, 0)}-${bytesToHexNum(randomBytes, 2, 4)}-${
        bytesToHexNum(
            randomBytes,
            2,
            6
        )
    }-${bytesToHexNum(randomBytes, 2, 8)}-${bytesToHexNum(randomBytes, 6, 10)}"
}

@OptIn(ExperimentalUnsignedTypes::class)
fun bytesToHexNum(bytes: UByteArray, length: Int, offset: Int): String {
    return bytes.copyOfRange(offset, offset + length).joinToString("") { it.toString(16).padStart(2, '0') }
}

fun fileExtension(path: String): String {
    return path.substring(path.lastIndexOf('.') + 1)
}