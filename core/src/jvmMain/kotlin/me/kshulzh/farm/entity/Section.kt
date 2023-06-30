package me.kshulzh.farm.entity

open class Section : Entity() {
    var capacity: Map<String, Int> = mutableMapOf()
    var width: Double? = null
    var height: Double? = null
    var depth: Double? = null
}