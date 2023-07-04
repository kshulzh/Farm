package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Entity

interface EntityRepository<T : Entity> {
    fun save(entity: T?): T
    fun deleteById(id: String): T?
    fun findById(id: String): T?
    fun getAll(): List<T>
}