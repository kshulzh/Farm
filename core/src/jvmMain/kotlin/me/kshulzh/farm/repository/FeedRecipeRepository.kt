package me.kshulzh.farm.repository

import me.kshulzh.farm.entity.FeedRecipe
import org.springframework.stereotype.Repository

@Repository
interface FeedRecipeRepository : EntityRepository<FeedRecipe>