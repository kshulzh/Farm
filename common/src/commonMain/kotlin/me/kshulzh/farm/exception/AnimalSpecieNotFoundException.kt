package me.kshulzh.farm.exception

class AnimalSpecieNotFoundException(val id_:String) : NotFoundException("AnimalSpecie",id_)