package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.async.UnitBond
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.workshop.MasterFactory


class Scenes : ScenesAPI {

    val masterFactory = MasterFactory()

    //Temporary in memory scene specs
    val sceneSpecs: ObjectMap<String, SceneSpec> = ObjectMap()

    override fun instantiate(scene: SceneSpec, instanceParams: InstanceParams): UnitBond {
        masterFactory.createScene(scene, instanceParams)
        return UnitBond()
    }

    override fun instantiate(path: String, instanceParams: InstanceParams): UnitBond {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSceneSpec(path: String, scene: SceneSpec) {
        sceneSpecs.put(path, scene)
    }

    fun getScene(path: String): SceneSpec {
        return sceneSpecs.get(path)
    }
}