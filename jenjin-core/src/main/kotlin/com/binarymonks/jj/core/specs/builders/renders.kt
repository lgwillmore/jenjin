package com.binarymonks.jj.core.specs.builders

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.specs.render.CircleRenderNodeSpec
import com.binarymonks.jj.core.specs.render.PolygonRenderNodeSpec
import com.binarymonks.jj.core.specs.render.RenderSpec
import com.binarymonks.jj.core.specs.render.TextureNodeSpec


fun RenderSpec.imageTexture(assetpath: String, init: TextureNodeSpec.() -> Unit) {
    val imageSpec: TextureNodeSpec = TextureNodeSpec()
    imageSpec.assetPath = assetpath
    imageSpec.init()
    this.renderNodes.add(imageSpec)
}

fun RenderSpec.polygonRender(vertices: Array<Vector2>, init: PolygonRenderNodeSpec.() -> Unit) {
    val shapeSpec: PolygonRenderNodeSpec = PolygonRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    shapeSpec.init()
    this.renderNodes.add(shapeSpec)
}
fun RenderSpec.polygonRender(vararg vertices: Vector2, init: PolygonRenderNodeSpec.() -> Unit) {
    val shapeSpec: PolygonRenderNodeSpec = PolygonRenderNodeSpec()
    vertices.forEach { shapeSpec.vertices.add(it) }
    shapeSpec.init()
    this.renderNodes.add(shapeSpec)
}


fun RenderSpec.circleRender(radius: Float, init: CircleRenderNodeSpec.() -> Unit) {
    val circleRender = CircleRenderNodeSpec()
    circleRender.radius=radius
    circleRender.init()
    this.renderNodes.add(circleRender)
}


