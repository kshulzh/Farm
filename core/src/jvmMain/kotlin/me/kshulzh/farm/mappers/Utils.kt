package me.kshulzh.farm.mappers

import java.time.LocalDateTime

fun mapStringToDate(date: String?) = if (date != null) LocalDateTime.parse(date) else null

fun mapDateToString(date: LocalDateTime?) = date?.toString()

fun fromWeigth(string: String): Double {
    return string.toDouble()
}

fun toWeight(double: Double): String {
    return double.toString()
}