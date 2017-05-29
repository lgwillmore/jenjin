package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.render.RenderSpec
import com.binarymonks.jj.core.specs.render.ShapeNodeSpec
import com.binarymonks.jj.core.specs.render.TextureNodeSpec


fun RenderSpec.imageTexture(assetpath: String, init: TextureNodeSpec.() -> Unit) {
    val imageSpec: TextureNodeSpec = TextureNodeSpec()
    imageSpec.assetPath=assetpath
    imageSpec.init()
    this.renderNodes.add(imageSpec)
}

fun RenderSpec.shape(init: ShapeNodeSpec.()->Unit){
    val shapeSpec: ShapeNodeSpec = ShapeNodeSpec()
    shapeSpec.init()
    this.renderNodes.add(shapeSpec)
}


