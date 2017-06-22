package com.binarymonks.jj.core.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.ObjectSet
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.components.input.TouchHandler
import com.binarymonks.jj.core.extensions.unproject
import com.binarymonks.jj.core.physics.PhysicsNode
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.scenes.Scene

class TouchManager(internal var camera: OrthographicCamera) : InputProcessor {
    internal var touchTracker = ObjectMap<Int, Touch>(10)
    internal var touchRemovals = Array<Int>(10)


    internal var testPoint = new(Vector3::class)
    internal var testPoint2 = new(Vector2::class)
    internal var touchOffset = new(Vector2::class)
    internal var possibleBodies = ObjectSet<Fixture>()
    internal var touchedScene: Scene? = null


    override fun touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        unproject(x, y)
        checkForDragableThings(button)
        if (touchedScene != null) {
            touchTracker.put(pointer, new(Touch::class).set(touchedScene!!, touchOffset))
            touchedScene = null
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (touchTracker.containsKey(pointer)) {
            val t = touchTracker.remove(pointer)
            val upWorld = vec2(screenX.toFloat(), screenY.toFloat())
            camera.unproject(upWorld)
            t.touchUp(upWorld, button)
            recycle(upWorld)
            recycle(t)
        }
        return true
    }

    fun update() {
        for (touch in touchTracker) {
            if (touch.value.touchedThing!!.isDestroyed) {
                touchRemovals.add(touch.key)
                continue
            }
            unproject(Gdx.input.getX(touch.key), Gdx.input.getY(touch.key))
            touch.value.move(testPoint2, touch.key)
        }
        for (touch in touchRemovals) {
            val t = touchTracker.remove(touch!!)
            recycle(t)
        }
        touchRemovals.clear()
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    protected fun unproject(x: Int, y: Int) {
        camera.unproject(testPoint.set(x.toFloat(), y.toFloat(), 0f))
        testPoint2.set(testPoint.x, testPoint.y)
    }

    private fun checkForDragableThings(button: Int) {
        JJ.B.physicsWorld.b2dworld.QueryAABB(this::reportFixture, testPoint.x - 0.3f, testPoint.y - 0.3f, testPoint.x + 0.3f, testPoint.y + 0.3f)
        selectDragable(button)
    }

    private fun selectDragable(button: Int) {
        for (fixture in possibleBodies) {
            val node = fixture.userData as PhysicsNode
            if (node != null) {
                val parent = checkNotNull(node!!.physicsRoot.parent)
                if (!parent.isDestroyed) {
                    val t = parent.getComponent(TouchHandler::class)
                    if (t != null) {
                        touchedScene = parent
                        val touchLocation = new(Vector2::class).set(testPoint.x, testPoint.y)
                        t!!.onTouchDown(testPoint.x, testPoint.y, button)
                        recycle(touchLocation)
                        val hitBody = fixture.body
                        val bodyPosition = new(Vector2::class).set(hitBody.position)
                        if (t!!.trackTouch) {
                            touchOffset.set(bodyPosition.sub(testPoint.x, testPoint.y))
                        } else {
                            touchOffset.set(0f, 0f)
                        }
                        recycle(bodyPosition)
                        break
                    }
                }
            }
        }
        possibleBodies.clear()
    }


    fun reportFixture(fixture: Fixture): Boolean {
        possibleBodies.add(fixture)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    class Touch : Poolable {
        internal var touchedThing: Scene? = null
        internal var testPointCache = new(Vector2::class)
        internal var touchOffset = new(Vector2::class)

        operator fun set(touchedThing: Scene, offset: Vector2): Touch {
            this.touchedThing = touchedThing
            this.touchOffset.set(offset)
            return this
        }


        override fun reset() {
            testPointCache.set(0f, 0f)
            touchOffset.set(0f, 0f)
            touchedThing = null
        }

        fun move(newTouchLocation: Vector2, button: Int) {
            testPointCache.set(newTouchLocation)
            val newPosition = new(Vector2::class).set(newTouchLocation).add(touchOffset)
            touchedThing!!.getComponent(TouchHandler::class)!!.onTouchMove(newPosition.x, newPosition.y, button)
            recycle(newPosition)
        }

        fun touchUp(upWorld: Vector2, button: Int) {
            upWorld.add(touchOffset)
            touchedThing!!.getComponent(TouchHandler::class)!!.onTouchUp(upWorld.x, upWorld.y, button)
        }
    }


}
