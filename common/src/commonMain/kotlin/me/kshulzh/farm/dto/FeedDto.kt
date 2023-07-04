package me.kshulzh.farm.dto

class FeedDto : EntityDto() {
    lateinit var ingredientTypeId: String
    lateinit var count: String
    lateinit var sectionId: String
    var time: String? = null
}