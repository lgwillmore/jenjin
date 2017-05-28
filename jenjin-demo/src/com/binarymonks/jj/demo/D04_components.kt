package com.binarymonks.jj.demo

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.properties.PropDelegate
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*

/**
 * Building [com.binarymonks.jj.core.components.Component]s is what lets you build the behaviour of your game. Along with
 * [com.binarymonks.jj.core.physics.CollisionHandler]s. But this is to demonstrate the former.
 *
 * Components are the primary building blocks of any special mechanics of your game which make it more than
 * just a 2d physics simulator.
 *
 * Here we build a simple back and forth movement component [Platform] for some platforms as an example.
 *
 * We build it in a way which makes it configurable as an instance.
 */
class D04_components : Game(MyConfig04.jjConfig) {

    public override fun gameOn() {

        JJ.scenes.addSceneSpec("platform", platform())

        JJ.scenes.loadAssetsNow()

        val initialSceneSpec = scene {
            nodeRef(params {
                x = -10f
                y = 15f
                scaleX = 3f
                setProperty("direction", Direction.VERTICAL)
                setProperty("movementRange", 5f)
                setProperty("metersPerSecond", 1f)

            }) { "platform" }
            nodeRef(params {
                x = 0f
                y = 4f
                scaleX = 6f
                setProperty("direction", Direction.HORIZONTAL)
                setProperty("movementRange", 10f)
                setProperty("metersPerSecond", 3f)
            }) { "platform" }

        }

        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun platform(): SceneSpec {
        return scene {
            thing {
                physics {
                    // Kinematic is suited for direct movement of physics objects
                    bodyType = BodyDef.BodyType.KinematicBody
                    fixture {
                    }
                }
                //Add our component to our thing and bind fields to properties
                component(Platform()) {
                    direction.setToPropRef("direction")
                    movementRange.setToPropRef("movementRange")
                    metersPerSecond.setToPropRef("metersPerSecond")
                }
            }
        }
    }
}

enum class Direction {HORIZONTAL, VERTICAL }


//The component that controls the behaviour of our platform
class Platform : Component() {

    //We want our component to have the option of delegating its values to properties.
    // These are our configurable fields.
    var direction: PropDelegate<Direction> = PropDelegate(Direction.HORIZONTAL)
    var movementRange: PropDelegate<Float> = PropDelegate(0f)
    var metersPerSecond: PropDelegate<Float> = PropDelegate(0f)

    //These are our private fields for the running state
    private var startLocation: Vector2 = new(Vector2::class)
    private var velocity: Vector2 = new(Vector2::class)

    //For our platform we want to know where we start in the world so we can control movement
    // relative to that
    override fun onAddToWorld() {
        startLocation.set(myThing().physicsRoot.position())
        when (direction.resolve()) {
            Direction.VERTICAL -> velocity.set(0f, metersPerSecond.resolve())
            Direction.HORIZONTAL -> velocity.set(metersPerSecond.resolve(), 0F)
        }
    }

    override fun update() {
        val distanceFromStart = myThing().physicsRoot.position().sub(startLocation).len()
        if (distanceFromStart > movementRange.resolve()) {
            velocity.scl(-1f)
        }
        myThing().physicsRoot.b2DBody.linearVelocity = velocity
    }

    //We clone our config fields
    override fun clone(): Component {
        val duplicate = Platform()
        duplicate.direction.copyFrom(direction)
        duplicate.movementRange.copyFrom(movementRange)
        duplicate.metersPerSecond.copyFrom(metersPerSecond)
        return duplicate
    }
}


object MyConfig04 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 30f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 15f
    }
}
