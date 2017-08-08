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

    var touchBoxWidth = 0.1f

    internal var touchTracker = ObjectMap<Int, Touch>(10)
    internal var touchRemovals = Array<Int>(10)
    internal var testPoint = new(Vector3::class)
    internal var testPoint2 = new(Vector2::class)
    internal var touchOffset = new(Vector2::class)
    internal var possibleBodies = ObjectSet<Fixture>()
    internal var touchedScene: Scene? = null


    override fun touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        unproject(x, y)
        checkForTouchHandler(button)
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
            if (touch.value.touchedScene!!.isDestroyed) {
                touchRemovals.add(touch.key)
                continue
            }
            unproject(Gdx.input.getX(touch.key), Gdx.input.getY(touch.key))
            touch.value.move(testPoint2, touch.key)
        }
        touchRemovals
                .map { touchTracker.remove(it!!) }
                .forEach { recycle(it) }
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

    private fun checkForTouchHandler(button: Int) {
        JJ.B.physicsWorld.b2dworld.QueryAABB(this::reportFixture, testPoint.x - (touchBoxWidth / 2), testPoint.y - (touchBoxWidth / 2), testPoint.x + (touchBoxWidth / 2), testPoint.y + (touchBoxWidth / 2))
        selectTouchHandler(button)
    }

    private fun selectTouchHandler(button: Int) {
        for (fixture in possibleBodies) {
            val node = fixture.userData as PhysicsNode
            val parent = checkNotNull(node.physicsRoot.parent)
            if (!parent.isDestroyed) {
                val t = parent.getComponent(TouchHandler::class)
                if (t != null) {
                    touchedScene = parent
                    val touchLocation = new(Vector2::class).set(testPoint.x, testPoint.y)
                    val touchHandled = t.onTouchDown(testPoint.x, testPoint.y, button)
                    recycle(touchLocation)
                    if (!touchHandled) continue
                    val hitBody = fixture.body
                    val bodyPosition = new(Vector2::class).set(hitBody.position)
                    if (t.relativeToTouch) {
                        touchOffset.set(bodyPosition.sub(testPoint.x, testPoint.y))
                    } else {
                        touchOffset.set(0f, 0f)
                    }
                    recycle(bodyPosition)
                    break
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
        internal var touchedScene: Scene? = null
        internal var testPointCache = new(Vector2::class)
        internal var touchOffset = new(Vector2::class)

        operator fun set(touchedScene: Scene, offset: Vector2): Touch {
            this.touchedScene = touchedScene
            this.touchOffset.set(offset)
            return this
        }


        override fun reset() {
            testPointCache.set(0f, 0f)
            touchOffset.set(0f, 0f)
            touchedScene = null
        }

        fun move(newTouchLocation: Vector2, button: Int) {
            testPointCache.set(newTouchLocation)
            val newPosition = new(Vector2::class).set(newTouchLocation).add(touchOffset)
            touchedScene!!.getComponent(TouchHandler::class)!!.onTouchMove(newPosition.x, newPosition.y, button)
            recycle(newPosition)
        }

        fun touchUp(upWorld: Vector2, button: Int) {
            upWorld.add(touchOffset)
            touchedScene!!.getComponent(TouchHandler::class)!!.onTouchUp(upWorld.x, upWorld.y, button)
        }
    }


}
