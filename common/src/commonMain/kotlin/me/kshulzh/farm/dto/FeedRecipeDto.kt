package me.kshulzh.farm.dto

class FeedRecipeDto : EntityDto(){
    var description: String? = null;
    var ingredients: Map<String,Double> = mutableMapOf()
}