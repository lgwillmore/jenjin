package com.binarymonks.jj.core.physics.collisions

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.binarymonks.jj.core.audio.SoundMode
import com.binarymonks.jj.core.things.Thing

/**
 * Triggers a sound on collision
 */
class SoundCollision(
        var soundName: String? = null,
        var mode: SoundMode = SoundMode.NORMAL
) : CollisionHandler() {

    override fun collision(me: Thing, myFixture: Fixture, other: Thing, otherFixture: Fixture, contact: Contact) {
        me.soundEffects.triggerSound(checkNotNull(soundName), mode)
    }

    override fun clone(): CollisionHandler {
        val copy = SoundCollision()
        copy.soundName = soundName
        copy.mode = mode
        copy.copyProperties(this)
        return copy
    }
}