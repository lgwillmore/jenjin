package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.async.Bond
import com.binarymonks.jj.core.async.UnitBond
import com.binarymonks.jj.core.scenes.Scene
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef


interface ScenesAPI {
    /**
     * Instantiate a [SceneSpec]
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun instantiate(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()): Bond<Scene>


    /**
     * Instantiate a [SceneSpec]
     *
     * @param path The path to the [SceneSpec]. These can be added with [addSceneSpec]
     * @param instanceParams The instance specific parameters
     */
    fun instantiate(path: String, instanceParams: InstanceParams = InstanceParams.new()): Bond<Scene>


    /**
     * Add a named [SceneSpecRef].
     */
    fun addSceneSpec(path: String, scene: SceneSpecRef)


    /**
     * Load any assets that can be identified in the currently loaded [SceneSpec]s.
     *
     * This is a synchronous call, so your code and your game will be halted where it is while this happens.
     */
    fun loadAssetsNow()
}
