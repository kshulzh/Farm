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

package me.kshulzh.farm.main.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "animals")
class Animal {
    @Id
    lateinit var id: String

    @Column(name = "gender")
    var gender: me.kshulzh.farm.main.entity.Gender = me.kshulzh.farm.main.entity.Gender.NONE

    @Column(name = "weight")
    var weight: Double? = null

    @ManyToOne
    var specie: AnimalSpecie? = null

    @Column(name = "type")
    var type: me.kshulzh.farm.main.entity.AnimalType? = null

    @ManyToOne
    var section: Section? = null
}