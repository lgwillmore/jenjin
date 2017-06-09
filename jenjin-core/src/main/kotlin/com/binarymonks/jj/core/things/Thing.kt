package com.binarymonks.jj.core.things

import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.ComponentMaster
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.properties.HasProps
import com.binarymonks.jj.core.render.RenderRoot

open class Thing(
        val uniqueName: String?,
        val physicsRoot: PhysicsRoot,
        val renderRoot: RenderRoot,
        val soundEffects: SoundEffects,
        val properties: ObjectMap<String, Any>,
        val pooled: Boolean = false
) : HasProps {


    var id = JJ.B.nextID()
    internal var componentMaster = ComponentMaster()
    private var isDestroyed: Boolean = false
    private var children: Array<Thing> = Array()
    private var parent: Thing? = null

    init {
        physicsRoot.parent = this
        renderRoot.parent = this
    }

    fun update() {
        componentMaster.update()
    }

    fun destroy() {
        if (parent == null) {
        }
        if (!isDestroyed) {
            isDestroyed = true
            JJ.B.thingWorld.remove(this)
            children.forEach {
                it.destroy()
            }
            if (parent != null && !checkNotNull(parent).isDestroyed) {
                checkNotNull(parent).children.removeValue(this, true)
            }
        }
    }

    fun addComponent(component: Component) {
        component.parent = this
        componentMaster.addComponent(component)
    }

    override fun hasProp(key: String): Boolean {
        return properties.containsKey(key)
    }

    override fun getProp(key: String): Any? {
        return properties.get(key)
    }

    fun addChild(nodeThing: Thing) {
        children.add(nodeThing)
        nodeThing.parent = this
    }

    internal fun executeDestruction() {
        renderRoot.destroy(pooled)
        physicsRoot.destroy(pooled)
        componentMaster.neutralise()
    }

    internal fun resetFromPool(x: Float, y: Float, rotationD: Float) {
        physicsRoot.reset(x, y, rotationD)
        componentMaster.reactivate()
    }


}