package com.binarymonks.jj.core

import com.badlogic.gdx.ApplicationListener
import com.binarymonks.jj.core.specs.SceneSpec

/**
 * A Game
 *
 * You must extend this and only ever create one of them. It is the entry point to Jenjin and it initializes
 * a bunch of global state - which must only be done once.
 *
 * @property jjConfig Configuration for your game.
 */
abstract class Game (val jjConfig: JJConfig) : ApplicationListener{

    override fun create() {
    }

    override fun pause() {
    }

    override fun resize(width: Int, height: Int) {
    }
    override fun render() {
    }

    override fun resume() {
    }

    override fun dispose() {
    }

    /**
     * This is your entry hook.
     *
     * You can instantiate assets and build your [SceneSpec]s here or whatever.
     * In the end though, you need to probably do 1 of 3 things:
     *
     *  - Load a [SceneSpec] or
     *  - Present a Splash Screen or
     *  - Present a Menu
     *
     */
    protected abstract fun gameOn()

}


