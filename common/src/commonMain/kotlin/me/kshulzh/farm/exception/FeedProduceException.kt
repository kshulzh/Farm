package me.kshulzh.farm.exception

class FeedProduceException(val name_: String) : ProduceException("Feed", "Not enough $name_")