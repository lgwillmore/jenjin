package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Transform
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.pools.new
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
        val radius: Float
) : ShapeRenderNode(priority, color, fill) {

    private var positionCache: Vector2 = new(Vector2::class)
    private var position3Cache: Vector3 = new(Vector3::class)

    override fun drawShape(camera: OrthographicCamera) {
        val transform: Transform = myParent().physicsRoot.transform
        positionCache.set(offsetX,offsetY)
        transform.mul(positionCache)
        position3Cache.set(positionCache.x,positionCache.y,0f)
        camera.project(position3Cache)
        JJ.B.renderWorld.shapeRenderer.circle(position3Cache.x,position3Cache.y,radius * JJ.B.renderWorld.worldToScreenScale)
    }

    override fun dispose() {
    }

}