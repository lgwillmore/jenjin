package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.async.Bond
import com.binarymonks.jj.core.async.OneTimeTask
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.workshop.MasterFactory


class Scenes : ScenesAPI {

    val masterFactory = MasterFactory()
    //Temporary in memory scene specs
    val sceneSpecs: ObjectMap<String, SceneSpecRef> = ObjectMap()

    private var dirty = false

    override fun instantiate(scene: SceneSpec, instanceParams: InstanceParams): Bond<Scene> {
        val delayedCreate = new(CreateSceneFunction::class)
        val bond = new(Bond::class) as Bond<Scene>
        delayedCreate.set(scene, instanceParams, bond)
        if (!JJ.B.physicsWorld.isUpdating) {
            JJ.tasks.addPrePhysicsTask(delayedCreate)
        } else {
            JJ.tasks.addPostPhysicsTask(delayedCreate)
        }
        return bond
    }

    override fun instantiate(path: String, instanceParams: InstanceParams): Bond<Scene> {
        return instantiate(sceneSpecs.get(path).resolve(),instanceParams)
    }

    override fun addSceneSpec(path: String, scene: SceneSpecRef) {
        sceneSpecs.put(path, scene)
        dirty = true
    }

    fun getScene(path: String): SceneSpec {
        return sceneSpecs.get(path).resolve()
    }

    override fun loadAssetsNow() {
        if (dirty) {
            var assets: Array<AssetReference> = getAllAssets()
            JJ.B.assets.loadNow(assets)
        }
        dirty = false
    }

    private fun getAllAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = Array()
        for (entry in sceneSpecs) {
            assets.addAll(entry.value.getAssets())
        }
        return assets
    }
}

class CreateSceneFunction : OneTimeTask(), Poolable {
    override fun doOnce() {
        val scene = JJ.B.scenes.masterFactory.createScene(sceneSpec!!, instanceParams!!)
        bond!!.succeed(scene)
        recycle(instanceParams!!)
        recycle(this)
    }

    override fun reset() {
        sceneSpec = null
        instanceParams = null
        bond = null
    }

    internal var sceneSpec: SceneSpec? = null
    internal var instanceParams: InstanceParams? = null
    internal var bond: Bond<Scene>? = null

    operator fun set(sceneSpec: SceneSpec, instanceParams: InstanceParams, bond: Bond<Scene>) {
        this.sceneSpec = sceneSpec
        this.instanceParams = instanceParams.clone()
        this.bond = bond
    }
}