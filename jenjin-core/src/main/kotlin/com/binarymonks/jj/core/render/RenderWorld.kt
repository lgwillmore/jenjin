package com.binarymonks.jj.core.render

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.RenderAPI
import com.binarymonks.jj.core.things.Thing
import com.esotericsoftware.spine.SkeletonRenderer

open class RenderWorld : RenderAPI {
    var shapeRenderer = ShapeRenderer()
    var polyBatch = PolygonSpriteBatch()
    var skeletonRenderer = SkeletonRenderer<PolygonSpriteBatch>()
    var rayHandler: RayHandler = RayHandler(JJ.B.physicsWorld.b2dworld)
    var defaultRenderGraph = RenderGraph()
    var lightSourceRenderGraph = RenderGraph()
    var worldToScreenScale: Float = 0.toFloat()
    private var currentShapeFill = false
    private var batchStoredColor = Color.WHITE

    init {
        rayHandler.setBlurNum(3)
        rayHandler.setAmbientLight(0.0f, 0.0f, 0.0f, 1.0f)
    }

    fun addThing(thing: Thing) {
        defaultRenderGraph.add(thing.renderRoot.specID, thing.id, thing.renderRoot.defaultRenderLayers)
        lightSourceRenderGraph.add(thing.renderRoot.specID, thing.id, thing.renderRoot.lightRenderLayers)
    }

    fun removeThing(removal: Thing) {
        defaultRenderGraph.remove(removal.renderRoot.specID, removal.id, removal.renderRoot.defaultRenderLayers)
        lightSourceRenderGraph.remove(removal.renderRoot.specID, removal.id, removal.renderRoot.lightRenderLayers)
    }

    override fun setAmbientLight(r: Float, g: Float, b: Float, a: Float) {
        rayHandler.setAmbientLight(r, g, b, a)
    }

    fun switchToShapes(fill: Boolean) {
        if (!shapeRenderer.isDrawing) {
            polyBatch.end()
            shapeRenderer.begin(if (fill) ShapeRenderer.ShapeType.Filled else ShapeRenderer.ShapeType.Line)
            currentShapeFill = fill
        } else if (fill != currentShapeFill) {
            currentShapeFill = fill
            shapeRenderer.end()
            shapeRenderer.begin(if (fill) ShapeRenderer.ShapeType.Filled else ShapeRenderer.ShapeType.Line)
        }
    }

    fun switchToBatch() {
        if (!polyBatch.isDrawing) {
            shapeRenderer.end()
            polyBatch.begin()
        }
    }

    fun switchBatchColorTo(color: Color) {
        batchStoredColor = polyBatch.color
        polyBatch.color = color
    }

    fun switchBatchColorBack() {
        polyBatch.color = batchStoredColor
    }

    fun end() {
        if (polyBatch.isDrawing) {
            polyBatch.end()
        } else {
            shapeRenderer.end()
        }
    }
}
