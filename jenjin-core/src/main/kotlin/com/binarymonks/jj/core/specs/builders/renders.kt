package com.binarymonks.jj.core.specs.builders

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.render.*


fun RenderSpec.imageTexture(assetpath: String, init: (TextureNodeSpec.() -> Unit)? = null) {
    val imageSpec: TextureNodeSpec = TextureNodeSpec()
    imageSpec.assetPath = assetpath
    if (init != null) {
        imageSpec.init()
    }
    this.renderNodes.add(imageSpec)
}

fun RenderSpec.polygonRender(vertices: Array<Vector2>, init: (PolygonRenderNodeSpec.() -> Unit)? = null) {
    val shapeSpec: PolygonRenderNodeSpec = PolygonRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    if (init != null) {
        shapeSpec.init()
    }
    this.renderNodes.add(shapeSpec)
}

fun RenderSpec.polygonRender(vararg vertices: Vector2, init: (PolygonRenderNodeSpec.() -> Unit)? = null) {
    val shapeSpec: PolygonRenderNodeSpec = PolygonRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    if (init != null) {
        shapeSpec.init()
    }
    this.renderNodes.add(shapeSpec)
}

fun RenderSpec.lineChainRender(vertices: Array<Vector2>, init: (LineChainRenderNodeSpec.() -> Unit)? = null) {
    val shapeSpec: LineChainRenderNodeSpec = LineChainRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    if (init != null) {
        shapeSpec.init()
    }
    this.renderNodes.add(shapeSpec)
}

fun RenderSpec.lineChainRender(vararg vertices: Vector2, init: (LineChainRenderNodeSpec.() -> Unit)? = null) {
    val shapeSpec: LineChainRenderNodeSpec = LineChainRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    if (init != null) {
        shapeSpec.init()
    }
    this.renderNodes.add(shapeSpec)
}

fun RenderSpec.rectangleRender(width: Float, height: Float, init: (PolygonRenderNodeSpec.() -> Unit)? = null) {
    val shapeSpec: PolygonRenderNodeSpec = PolygonRenderNodeSpec()
    shapeSpec.vertices.add(vec2(-width / 2, height / 2))
    shapeSpec.vertices.add(vec2(width / 2, height / 2))
    shapeSpec.vertices.add(vec2(width / 2, -height / 2))
    shapeSpec.vertices.add(vec2(-width / 2, -height / 2))
    if (init != null) {
        shapeSpec.init()
    }
    this.renderNodes.add(shapeSpec)
}


fun RenderSpec.circleRender(radius: Float, init: (CircleRenderNodeSpec.() -> Unit)? = null) {
    val circleRender = CircleRenderNodeSpec()
    circleRender.radius = radius
    if (init != null) {
        circleRender.init()
    }
    this.renderNodes.add(circleRender)
}


