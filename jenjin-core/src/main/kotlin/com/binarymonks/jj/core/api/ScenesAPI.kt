package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.api.specs.InstanceParams
import com.binarymonks.jj.core.api.specs.SceneSpec
import com.binarymonks.jj.core.async.UnitBond


interface ScenesAPI {
    /**
     * Instantiate a [SceneSpec]
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun instantiate(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()): UnitBond


    /**
     * Instantiate a [SceneSpec]
     *
     * @param path The path to the [SceneSpec]. These can be added with [addSceneSpec]
     * @param instanceParams The instance specific parameters
     */
    fun instantiate(path: String, instanceParams: InstanceParams = InstanceParams.new()): UnitBond


    fun addSceneSpec(path: String, scene: SceneSpec)
}