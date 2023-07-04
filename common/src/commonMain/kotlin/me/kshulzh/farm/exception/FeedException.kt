package me.kshulzh.farm.exception

class FeedException(val name: String) : FarmException() {
    override val message: String
        get() = "Can not feed $name not enough leftovers"
}