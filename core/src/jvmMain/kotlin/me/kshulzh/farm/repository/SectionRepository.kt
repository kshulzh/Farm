package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Section
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository : EntityRepository<Section> {
}