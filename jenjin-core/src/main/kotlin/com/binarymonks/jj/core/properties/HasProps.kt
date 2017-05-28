package com.binarymonks.jj.core.properties

import com.badlogic.gdx.utils.ObjectMap


interface HasProps {
    fun fetchProps(): ObjectMap<String, Any>
}