package com.binarymonks.jj.core.scenes

import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.async.UnitBond
import com.binarymonks.jj.core.specs.InstanceParams


class Scenes: ScenesAPI {
    override fun instantiate(scene: SceneSpec, instanceParams: InstanceParams): UnitBond {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiate(path: String, instanceParams: InstanceParams): UnitBond {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSceneSpec(path: String, scene: SceneSpec) {
        TODO("not implemented") //To change physics of created functions use File | Settings | File Templates.
    }
}