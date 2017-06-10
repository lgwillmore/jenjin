package com.binarymonks.jj.core.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.api.AssetsAPI
import kotlin.reflect.KClass


class Assets : AssetsAPI {

    var manager: AssetManager

    init {
        val resolver = InternalFileHandleResolver()
        manager = com.badlogic.gdx.assets.AssetManager(resolver)
    }

    fun update() {
        manager.update()
    }

    override fun <T : Any> getAsset(path: String, kClass: KClass<T>): T {
        return manager.get(path, kClass.java)
    }

    override fun loadNow(path: String, assetClass: KClass<*>) {
        loadNow(AssetReference(assetClass, path))
    }

    fun loadNow(assetRef: AssetReference) {
        manager.load(assetRef.assetPath, assetRef.clazz.java)
        manager.finishLoading()
    }

    fun loadNow(assets: Array<AssetReference>) {
        for (ref in assets) {
            manager.load(ref.assetPath, ref.clazz.java)
        }
        manager.finishLoading()
    }
}