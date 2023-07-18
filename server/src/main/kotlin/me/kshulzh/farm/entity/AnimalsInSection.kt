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

package me.kshulzh.farm.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class AnimalsInSection {
    @Id
    lateinit var id: String
    lateinit var startDate: LocalDateTime
    var endDate: LocalDateTime? = null

    @ManyToOne
    lateinit var animal: Animal

    @ManyToOne
    var section: Section? = null
}