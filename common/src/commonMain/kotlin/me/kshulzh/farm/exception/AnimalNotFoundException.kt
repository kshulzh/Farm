package me.kshulzh.farm.exception

class AnimalNotFoundException(val id_: String) : NotFoundException("Animal", id_)