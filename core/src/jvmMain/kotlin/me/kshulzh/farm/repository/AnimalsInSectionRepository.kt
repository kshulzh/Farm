package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.AnimalsInSection
import org.springframework.stereotype.Repository

@Repository
interface AnimalsInSectionRepository : EntityRepository<AnimalsInSection> {
    fun getSectionIdByAnimalId(id: String): AnimalsInSection?
}