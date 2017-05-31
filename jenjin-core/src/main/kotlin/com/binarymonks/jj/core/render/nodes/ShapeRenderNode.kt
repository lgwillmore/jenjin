package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Transform
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.pools.recycleItems
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.properties.PropOverride


abstract class ShapeRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        var fill: Boolean = true
) : RenderNode(priority, color) {

    override fun render(camera: OrthographicCamera) {
        JJ.B.renderWorld.switchToShapes(fill)
        JJ.B.renderWorld.shapeRenderer.color = color.get()
        drawShape(camera)
    }

    abstract fun drawShape(camera: OrthographicCamera)
}

class CircleRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        fill: Boolean = true,
        val offsetX: Float,
        val offsetY: Float,
        val radius: Float,
        var segments: Int = 360
) : ShapeRenderNode(priority, color, fill) {

    private var positionCache: Vector2 = new(Vector2::class)

    override fun drawShape(camera: OrthographicCamera) {
        val transform: Transform = myParent().physicsRoot.transform
        positionCache.set(offsetX, offsetY)
        transform.mul(positionCache)
        JJ.B.renderWorld.shapeRenderer.circle(positionCache.x, positionCache.y, radius, segments)
    }

    override fun dispose() {
    }
}

class LineChainRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        fill: Boolean = false,
        internal var scaleX: Float = 1f,
        internal var scaleY: Float = 1f,
        internal var offsetX: Float = 0f,
        internal var offsetY: Float = 0f,
        internal var rotationD: Float = 0f,
        val vertices: Array<Vector2>
) : ShapeRenderNode(priority, color, fill) {

    private var vertCache: Array<Vector2> = Array()

    override fun drawShape(camera: OrthographicCamera) {
        val transform: Transform = myParent().physicsRoot.transform
        val localTransform = new(Matrix3::class)
        localTransform.scale(scaleX,scaleY)
        localTransform.translate(offsetX,offsetY)
        localTransform.rotate(rotationD)
        clearCache()
        vertices.forEach {
            val transformed = vec2().set(it)
            transform.mul(transformed)
            transformed.mul(localTransform)
            vertCache.add(transformed)
        }
        for (i in 1..vertCache.size - 1) {
            JJ.B.renderWorld.shapeRenderer.line(vertCache.get(i - 1), vertCache.get(i))
        }
        recycle(localTransform)
    }

    override fun dispose() {
    }

    private fun clearCache() {
        recycleItems(vertCache)
        vertCache.clear()
    }
}