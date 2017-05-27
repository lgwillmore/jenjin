package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ


class RenderSpec {
    var id = JJ.B.nextID()
    var renderNodes: Array<RenderNodeSpec> = Array()
}