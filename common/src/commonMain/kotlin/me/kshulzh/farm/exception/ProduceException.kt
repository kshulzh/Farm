package me.kshulzh.farm.exception

open class ProduceException(val name: String, val reason: String) : FarmException() {
    override val message: String?
        get() = "Could not produce $name. $reason"
}