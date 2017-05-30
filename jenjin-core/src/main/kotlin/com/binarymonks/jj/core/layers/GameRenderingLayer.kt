package com.binarymonks.jj.core.layers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.GameViewConfig
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.render.RenderGraph
import com.binarymonks.jj.core.render.RenderLayer
import com.binarymonks.jj.core.render.nodes.RenderNode

class GameRenderingLayer(
        worldBoxWidth: Float,
        posX: Float,
        posY: Float
) : Layer() {
    var camera: OrthographicCamera = OrthographicCamera()

    internal var drenderer = Box2DDebugRenderer()

    init {
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        setView(worldBoxWidth, posX, posY)
    }

    constructor(viewConfig: GameViewConfig) : this(
            viewConfig.worldBoxWidth,
            viewConfig.cameraPosX,
            viewConfig.cameraPosY)


    override fun update() {
        camera.update()
        updateScreenToWorldScale()
        renderGraph(JJ.B.renderWorld.defaultRenderGraph)
        renderLights()
        renderGraph(JJ.B.renderWorld.lightSourceRenderGraph)
        if (JJ.B.config.b2dConfig.debug) {
            drenderer.render(JJ.B.physicsWorld.b2dworld, JJ.B.renderWorld.polyBatch.getProjectionMatrix())
        }
    }

    private fun updateScreenToWorldScale() {
        val worldDistance = 1000f
        val worldLeft = new(Vector3::class).set(0f, 0f, 0f)
        val worldRight = new(Vector3::class).set(worldDistance, 0f, 0f)
        val screenLeft = camera.project(worldLeft)
        val screenRight = camera.project(worldRight)
        JJ.B.renderWorld.worldToScreenScale = (screenRight.x - screenLeft.x) / worldDistance
        recycle(worldLeft, worldRight)
    }

    private fun renderGraph(renderGraph: RenderGraph) {
        val layers = renderGraph.graphLayers
        JJ.B.renderWorld.polyBatch.enableBlending()
        JJ.B.renderWorld.polyBatch.setProjectionMatrix(camera.combined)
        JJ.B.renderWorld.polyBatch.begin()
        var renderedCount = 0
        var layerIndex = 0
        while (renderedCount < layers.size) {
            if (layers.containsKey(layerIndex)) {
                renderedCount++
                updateLayer(layers.get(layerIndex))
            }
            layerIndex++
        }
        JJ.B.renderWorld.end()
    }

    private fun renderLights() {
        JJ.B.renderWorld.rayHandler.setCombinedMatrix(camera.combined, camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight)
        JJ.B.renderWorld.rayHandler.updateAndRender()
    }

    private fun updateLayer(layer: RenderGraph.GraphLayer) {
        for (componentsByThing in layer.thingLayersByThingPathAndID) {
            updateThingLayers(componentsByThing.value)
        }
    }

    private fun updateThingLayers(thingLayers: ObjectMap<Int, RenderLayer>) {
        for (thingLayer in thingLayers) {
            updateThingNodes(thingLayer.value.renderNodes)
        }
    }

    private fun updateThingNodes(renderNodes: Array<RenderNode>) {
        for (node in renderNodes) {
            node.render(camera)
        }
    }

    fun setView(worldWidth: Float, cameraX: Float, cameraY: Float) {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()
        camera.viewportWidth = worldWidth
        camera.viewportHeight = worldWidth * (h / w)
        camera.position.set(cameraX, cameraY, 0f)
        camera.update()
    }
}
