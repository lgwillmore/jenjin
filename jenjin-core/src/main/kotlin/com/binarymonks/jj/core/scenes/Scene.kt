package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.ComponentMaster
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.properties.HasProps
import com.binarymonks.jj.core.render.RenderRoot
import com.binarymonks.jj.core.utils.NamedArray
import kotlin.reflect.KClass

open class Scene(
        var name: String?,
        var specName: String?,
        var uniqueName: String?,
        val physicsRoot: PhysicsRoot,
        val renderRoot: RenderRoot,
        val soundEffects: SoundEffects,
        val properties: ObjectMap<String, Any>,
        val pooled: Boolean = false
) : HasProps {


    var id = JJ.B.nextID()
    internal var componentMaster = ComponentMaster()
    var isDestroyed: Boolean = false
        private set
    private var children: NamedArray<Scene> = NamedArray()
    private var parent: Scene? = null

    init {
        physicsRoot.parent = this
        renderRoot.parent = this
    }

    fun update() {
        componentMaster.update()
    }

    fun destroy() {
        if (!isDestroyed) {
            isDestroyed = true
            com.binarymonks.jj.core.JJ.B.sceneWorld.remove(this)
            children.forEach {
                it.destroy()
            }
            if (parent != null && !checkNotNull(parent).isDestroyed) {
                checkNotNull(parent).children.removeValue(this, true)
            }
        }
    }

    fun addComponent(component: Component) {
        component.scene = this
        componentMaster.addComponent(component)
    }

    fun <T : Component> getComponent(type: KClass<T>): T? {
        return componentMaster.getComponent(type)
    }

    override fun hasProp(key: String): Boolean {
        return properties.containsKey(key)
    }

    override fun getProp(key: String): Any? {
        return properties.get(key)
    }

    fun addChild(nodeScene: Scene) {
        if (nodeScene.name != null) {
            children.add(nodeScene.name!!, nodeScene)
        } else {
            children.add(nodeScene)
        }
        nodeScene.parent = this
    }

    fun getChild(name: String): Scene? {
        return children.get(name)
    }

    fun getChild(index: Int): Scene {
        return children[index]
    }

    fun parent(): Scene {
        return checkNotNull(parent)
    }

    fun getNode(path: ScenePath): Scene {
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
        if (other !is Scene) return false

        if (name != other.name) return false
        if (uniqueName != other.uniqueName) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Scene(name=$name, uniqueName=$uniqueName, id=$id)"
    }


}