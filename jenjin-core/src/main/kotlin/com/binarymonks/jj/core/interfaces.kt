package com.binarymonks.jj.core


interface Copyable<out T> {
    fun copy(): T
}