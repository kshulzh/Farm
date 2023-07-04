package me.kshulzh.farm.exception

open class NotFoundException(val name: String, val id: String) : FarmException() {
    override val message: String?
        get() = "$name with id = $id not found"
}