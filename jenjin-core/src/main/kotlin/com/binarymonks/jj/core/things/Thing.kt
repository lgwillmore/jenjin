package com.binarymonks.jj.core.things

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.ComponentMaster
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.properties.HasProps
import com.binarymonks.jj.core.render.RenderRoot

class Thing(
        val uniqueName: String?,
        val physicsRoot: PhysicsRoot,
        val renderRoot: RenderRoot,
        val soundEffects: SoundEffects,
        val properties:ObjectMap<String,Any>
) : HasProps {


    var id = JJ.B.nextID()
    private var componentMaster = ComponentMaster()

    init {
        physicsRoot.parent = this
        renderRoot.parent = this
    }

    fun hasProperty(propertyKey: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun update() {
        componentMaster.update()
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


}