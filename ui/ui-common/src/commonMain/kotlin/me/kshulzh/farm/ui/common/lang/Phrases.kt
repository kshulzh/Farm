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

package me.kshulzh.farm.ui.common.lang

interface Phrases {
    companion object {
        val LANGS = mutableMapOf("en" to EnPhrases)
    }

    val ID: String
    val SAVE: String
    val NAME: String
    val DESCRIPTION: String
    val UNNAMED: String
    val PRICE: String
    val ITEM_CODE: String
    val SPENT_MONEY: String
    val SETTINGS: String
    val CANCEL: String
    val ITEMS: String
    val DELETE: String
    val MENU: String
    val NEW: String
    val NUMBER: String
    val CAPACITY: String
    val WIDTH: String
    val HEIGHT: String
    val DEPTH: String
}