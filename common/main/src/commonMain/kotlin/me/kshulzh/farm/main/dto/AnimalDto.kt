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
package me.kshulzh.farm.main.dto

import me.kshulzh.farm.common.dto.EntityDto
import me.kshulzh.farm.main.entity.AnimalState
import me.kshulzh.farm.main.entity.AnimalType
import me.kshulzh.farm.main.entity.Gender


class AnimalDto : EntityDto() {
    var gender: String = Gender.NONE.name
    var weight: Double? = null
    var specieId: String? = null
    var type: AnimalType? = null
    var state: AnimalState? = null
}