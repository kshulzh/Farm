package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.Feed
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : EntityRepository<Feed>