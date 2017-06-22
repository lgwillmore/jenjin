package com.binarymonks.jj.core.scenes

open class Scene(
        var name: String?,
        var uniqueName: String?,
        val physicsRoot: com.binarymonks.jj.core.physics.PhysicsRoot,
        val renderRoot: com.binarymonks.jj.core.render.RenderRoot,
        val soundEffects: com.binarymonks.jj.core.audio.SoundEffects,
        val properties: com.badlogic.gdx.utils.ObjectMap<String, Any>,
        val pooled: Boolean = false
) : com.binarymonks.jj.core.properties.HasProps {


    var id = com.binarymonks.jj.core.JJ.B.nextID()
    internal var componentMaster = com.binarymonks.jj.core.components.ComponentMaster()
    var isDestroyed: Boolean = false
        private set
    private var children: com.binarymonks.jj.core.utils.NamedArray<Scene> = com.binarymonks.jj.core.utils.NamedArray()
    private var parent: com.binarymonks.jj.core.scenes.Scene? = null

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

    fun addComponent(component: com.binarymonks.jj.core.components.Component) {
        component.scene = this
        componentMaster.addComponent(component)
    }

    fun <T: com.binarymonks.jj.core.components.Component> getComponent(type: kotlin.reflect.KClass<T>): T?{
        return componentMaster.getComponent(type)
    }

    override fun hasProp(key: String): Boolean {
        return properties.containsKey(key)
    }

    override fun getProp(key: String): Any? {
        return properties.get(key)
    }

    fun addChild(nodeScene: com.binarymonks.jj.core.scenes.Scene) {
        if (nodeScene.name != null) {
            children.add(nodeScene.name!!, nodeScene)
        } else {
            children.add(nodeScene)
        }
        nodeScene.parent = this
    }

    fun getChild(name: String): com.binarymonks.jj.core.scenes.Scene? {
        return children.get(name)
    }

    fun getChild(index: Int): com.binarymonks.jj.core.scenes.Scene {
        return children[index]
    }

    fun parent(): com.binarymonks.jj.core.scenes.Scene {
        return checkNotNull(parent)
    }

    fun getNode(path: com.binarymonks.jj.core.scenes.ScenePath): com.binarymonks.jj.core.scenes.Scene {
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
        if (other !is com.binarymonks.jj.core.scenes.Scene) return false

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