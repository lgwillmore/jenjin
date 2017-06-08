package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.async.UnitBond
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.ThingSpec
import com.binarymonks.jj.core.workshop.MasterFactory


class Scenes : ScenesAPI {

    val masterFactory = MasterFactory()
    //Temporary in memory scene specs
    val sceneSpecs: ObjectMap<String, SceneSpec> = ObjectMap()

    private var dirty = false

    override fun instantiate(scene: SceneSpec, instanceParams: InstanceParams): UnitBond {
        masterFactory.createScene(scene, instanceParams)
        return UnitBond()
    }

    override fun instantiate(path: String, instanceParams: InstanceParams): UnitBond {
        masterFactory.createScene(path, instanceParams)
        return UnitBond()
    }

    override fun addSceneSpec(path: String, scene: SceneSpec) {
        sceneSpecs.put(path, scene)
        dirty = true
    }

    fun getScene(path: String): SceneSpec {
        return sceneSpecs.get(path)
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
            if (entry.value.thingSpec != null) {
                addRenderAssets(assets, entry.value.thingSpec!!)
                addSoundAssets(assets, entry.value.thingSpec!!)
            }
        }
        return assets
    }

    private fun addSoundAssets(assets: Array<AssetReference>, thingSpec: ThingSpec) {
        for (soundParams in thingSpec.sounds) {
            for (path in soundParams.soundPaths) {
                assets.add(AssetReference(Sound::class, path))
            }
        }
    }

    private fun addRenderAssets(assets: Array<AssetReference>, thingSpec: ThingSpec) {
        for (node in thingSpec.render.renderNodes) {
            assets.addAll(node.getAssets())
        }
    }
}