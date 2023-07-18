package me.kshulzh.farm.utils

import org.mockito.ArgumentCaptor

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()