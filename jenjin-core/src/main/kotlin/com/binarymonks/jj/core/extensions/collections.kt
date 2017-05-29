package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.utils.ObjectMap


fun <K,V> ObjectMap<K, V>.copy(): ObjectMap<K,V>{
    val clone : ObjectMap<K,V> = ObjectMap()
    for(entry in this){
        clone.put(entry.key,entry.value)
    }
    return clone
}


