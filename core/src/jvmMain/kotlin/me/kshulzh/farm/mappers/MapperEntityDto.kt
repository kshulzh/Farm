package me.kshulzh.farm.mappers

import me.kshulzh.farm.entity.Entity

interface MapperEntityDto<E: Entity, D> {
    fun mapToEntity(dto: D?) : E? {
        return if(dto == null) null else toEntity(dto)
    }
    fun mapToDto(entity: E?) : D? {
        return if(entity == null) null else toDto(entity)
    }

    fun toEntity(dto: D) : E
    fun toDto(entity: E) : D
}