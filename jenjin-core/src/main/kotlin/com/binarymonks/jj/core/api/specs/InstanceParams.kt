package com.binarymonks.jj.core.api.specs


class InstanceParams private constructor(){
    companion object Factory {
        fun new(): InstanceParams{
            return InstanceParams()
        }
    }
}