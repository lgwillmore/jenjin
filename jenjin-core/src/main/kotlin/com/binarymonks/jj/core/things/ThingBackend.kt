package com.binarymonks.jj.core.things




internal fun Thing.executeDestruction() {
    renderRoot.destroy(pooled)
    physicsRoot.destroy(pooled)
    componentMaster.neutralise()
}