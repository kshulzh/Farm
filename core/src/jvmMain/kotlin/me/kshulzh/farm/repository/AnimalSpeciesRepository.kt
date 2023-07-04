package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.AnimalSpecie
import org.springframework.stereotype.Repository

@Repository
interface AnimalSpeciesRepository : EntityRepository<AnimalSpecie>