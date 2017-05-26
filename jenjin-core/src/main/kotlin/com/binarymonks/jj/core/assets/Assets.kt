package com.binarymonks.jj.core.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.utils.Array
import kotlin.reflect.KClass


class Assets {

    var manager: AssetManager

    init {
        val resolver = InternalFileHandleResolver()
        manager = com.badlogic.gdx.assets.AssetManager(resolver)
    }

    fun update() {
        manager.update()
    }

    fun <T : Any> getAsset(path: String, kClass: KClass<T>): T {
        return manager.get(path, kClass.java)
    }

    fun loadNow(assets: Array<AssetReference>) {
        for (ref in assets) {
            manager.load(ref.assetPath, ref.clazz.java)
        }
        manager.finishLoading()
    }
}