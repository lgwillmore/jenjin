package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.physics.*

fun PhysicsSpec.fixture(init: FixtureSpec.() -> Unit): FixtureSpec {
    var fixtureSpec = FixtureSpec()
    fixtureSpec.init()
    this.addFixture(fixtureSpec)
    return fixtureSpec
}

fun PhysicsSpec.pointLight(init: PointLightSpec.()->Unit){
    val pl = PointLightSpec()
    pl.init()
    this.lights.add(pl)
}


