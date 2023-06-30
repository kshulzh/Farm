package me.kshulzh.farm.exception

class SectionNotFoundException (val id_:String) : NotFoundException("Section",id_) {
}