package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Animal
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository : EntityRepository<Animal> {
}