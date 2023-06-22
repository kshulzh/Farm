package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Animal
import me.kshulzh.farm.entity.Entity

interface EntityRepository <T : Entity>{
    fun save(entity: T)
    fun deleteById(id: String)
    fun findById(id: String)
}