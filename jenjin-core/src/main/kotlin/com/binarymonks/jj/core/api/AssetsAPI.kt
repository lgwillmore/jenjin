package com.binarymonks.jj.core.api

import kotlin.reflect.KClass


interface AssetsAPI {

    fun loadNow(path: String, assetClass: KClass<*>)

    fun <T : Any> getAsset(path: String, assetClass: KClass<T>): T

}