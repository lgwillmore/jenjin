package com.binarymonks.jj.core.spine.components

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.scenes.Scene
import com.binarymonks.jj.core.scenes.ScenePath
import com.binarymonks.jj.core.spine.render.SpineRenderNode
import com.binarymonks.jj.core.spine.specs.SpineAnimations
import com.esotericsoftware.spine.AnimationState
import com.esotericsoftware.spine.AnimationStateData
import com.esotericsoftware.spine.Event


val SPINE_RENDER_NAME = "SPINE_RENDER_NODE"

class SpineComponent(
        var animations: SpineAnimations
) : Component() {

    constructor() : this(SpineAnimations())

    internal var spineBoneComponents: ObjectMap<String, SpineBoneComponent> = ObjectMap()
    lateinit internal var spineRenderNode: SpineRenderNode
    lateinit internal var animationState: AnimationState
    internal var ragDoll = false
    var bonePaths: ObjectMap<String, ScenePath> = ObjectMap()


    override fun onAddToWorld() {
        spineRenderNode = me().renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode
        bonePaths.forEach { it.value.from(me()).getComponent(SpineBoneComponent::class)!!.setSpineComponent(me()) }
        initialiseAnimations()
    }

    override fun update() {
        animationState.update(JJ.clock.deltaFloat)
        animationState.apply(spineRenderNode.skeleton)
    }

    override fun clone(): Component {
        val myClone = copy(this)
        myClone.animations = animations
        return myClone
    }

    private fun initialiseAnimations() {
        val stateData = AnimationStateData(spineRenderNode!!.skeletonData)
        animations.crossFades.forEach { stateData.setMix(it.fromName, it.toName, it.duration) }
        animationState = AnimationState(stateData)
        if (animations.startingAnimation != null) {
            animationState.setAnimation(0, animations.startingAnimation, true)
        }
        animationState.addListener(JJSpineAnimationStateListener(this, animations))
    }

    fun applyToBones(sceneOperation: (Scene) -> Unit) {
        bonePaths.forEach {
            sceneOperation(it.value.from(me()))
        }
    }

    fun addBone(name: String, spineBoneComponent: SpineBoneComponent) {
        spineBoneComponents.put(name, spineBoneComponent)
        spineBoneComponent.spineParent = this
    }

    fun getBone(name: String): SpineBoneComponent {
        return spineBoneComponents.get(name)
    }


    override fun onRemoveFromWorld() {
        spineBoneComponents.clear()
    }

    fun myRender(): SpineRenderNode {
        return checkNotNull(spineRenderNode)
    }

    fun reverseRagDoll() {
        if (ragDoll) {
            ragDoll = false
            for (partEntry in spineBoneComponents) {
                partEntry.value.reverseRagDoll()
            }
        }
    }

    fun triggerRagDoll(gravity: Float = 1f) {
        if (!ragDoll) {
            ragDoll = true
            for (partEntry in spineBoneComponents) {
                partEntry.value.triggerRagDoll(gravity)
            }
        }
    }
}

class JJSpineAnimationStateListener(
        val spineComponent: SpineComponent,
        val spineAnimations: SpineAnimations
) : AnimationState.AnimationStateListener {


    override fun start(entry: AnimationState.TrackEntry) {
    }

    override fun interrupt(entry: AnimationState.TrackEntry) {
    }

    override fun end(entry: AnimationState.TrackEntry) {
    }

    override fun dispose(entry: AnimationState.TrackEntry) {
    }

    override fun complete(entry: AnimationState.TrackEntry) {
    }

    override fun event(entry: AnimationState.TrackEntry, event: Event) {
        val name = event.data.name
        if (spineAnimations.functionHandlers.containsKey(name)) {
            spineAnimations.functionHandlers.get(name)(spineComponent, event)
        }
        if (spineAnimations.componentHandlers.containsKey(name)) {
            val mapping = spineAnimations.componentHandlers.get(name)
            val componentInstance = checkNotNull(spineComponent.me().getComponent(mapping.componentType))
            mapping.componentFunction.invoke(componentInstance, event)
        }
        if (spineAnimations.componentFunctions.containsKey(name)) {
            val mapping = spineAnimations.componentFunctions.get(name)
            val componentInstance = checkNotNull(spineComponent.me().getComponent(mapping.componentType))
            mapping.componentFunction.invoke(componentInstance)
        }
    }

}