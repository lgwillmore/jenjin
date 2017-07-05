package com.binarymonks.jj.core.physics

import com.badlogic.gdx.utils.Array


class CollisionHandlers {
    var preSolves = Array<PreSolveHandler>()
    var begins = Array<CollisionHandler>()
    var finalBegins = Array<CollisionHandler>()
    var postSolves = Array<PostSolveHandler>()
    var ends = Array<CollisionHandler>()

    fun copyAppendFrom(other: CollisionHandlers) {
        other.preSolves.forEach { preSolves.add(it.clone()) }
        other.begins.forEach { begins.add(it.clone()) }
        other.finalBegins.forEach { finalBegins.add(it.clone()) }
        other.ends.forEach { ends.add(it.clone()) }
        other.postSolves.forEach { postSolves.add(it.clone()) }
    }

    /**
     * Adds another [PreSolveHandler].
     * You can add as many as you want.
     *
     */
    fun presolve(handler: PreSolveHandler) {
        preSolves.add(handler)
    }

    /**
     * Adds another begin [CollisionHandler].
     * You can add as many as you want.
     *
     */
    fun begin(handler: CollisionHandler) {
        begins.add(handler)
    }

    /**
     * Adds another final begin [CollisionHandler].
     * You can add as many as you want.
     *
     * Called when a collision begins, but after the other objects begin collisions have been called.
     *
     * This is useful if this collision is going to destroy the other object for example.
     */
    fun finalBegin(handler: CollisionHandler) {
        finalBegins.add(handler)
    }

    /**
     * Adds another end [CollisionHandler].
     * You can add as many as you want.
     *
     */
    fun end(handler: CollisionHandler) {
        ends.add(handler)
    }

    /**
     * Adds another [PostSolveHandler].
     * You can add as many as you want.
     *
     */
    fun end(handler: PostSolveHandler) {
        postSolves.add(handler)
    }
}