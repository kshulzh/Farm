package me.kshulzh.farm.dto

class SectionDto : EntityDto() {
    var capacity: Map<String, Int> = mutableMapOf()
    var width: Double? = null
    var height: Double? = null
    var depth: Double? = null
}