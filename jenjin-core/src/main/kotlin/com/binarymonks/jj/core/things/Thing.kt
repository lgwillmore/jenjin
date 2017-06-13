package com.binarymonks.jj.core.things

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.ComponentMaster
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.properties.HasProps
import com.binarymonks.jj.core.render.RenderRoot
import com.binarymonks.jj.core.scenes.ScenePath
import com.binarymonks.jj.core.utils.NamedArray

open class Thing(
        var name: String?,
        var uniqueName: String?,
        val physicsRoot: PhysicsRoot,
        val renderRoot: RenderRoot,
        val soundEffects: SoundEffects,
        val properties: ObjectMap<String, Any>,
        val pooled: Boolean = false
) : HasProps {


    var id = JJ.B.nextID()
    internal var componentMaster = ComponentMaster()
    private var isDestroyed: Boolean = false
    private var children: NamedArray<Thing> = NamedArray()
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
        if (nodeThing.name != null) {
            children.add(nodeThing.name!!, nodeThing)
        } else {
            children.add(nodeThing)
        }
        nodeThing.parent = this
    }

    fun getChild(name: String): Thing? {
        return children.get(name)
    }

    fun getChild(index: Int): Thing {
        return children[index]
    }

    fun parent(): Thing {
        return checkNotNull(parent)
    }

    fun getNode(path: ScenePath): Thing{
        return path.from(this)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Thing) return false

        if (name != other.name) return false
        if (uniqueName != other.uniqueName) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Thing(name=$name, uniqueName=$uniqueName, id=$id)"
    }


}