package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.AnimalSpecies
import org.springframework.stereotype.Repository

@Repository
interface AnimalSpeciesRepository : EntityRepository<AnimalSpecies> {
}