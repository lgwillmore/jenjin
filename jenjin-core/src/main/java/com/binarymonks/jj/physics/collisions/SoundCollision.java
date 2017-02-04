package com.binarymonks.jj.physics.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.audio.SoundMode;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

public class SoundCollision extends CollisionFunction {

    String soundID;
    SoundMode mode = SoundMode.NORMAL;

    public SoundCollision(String soundID, SoundMode mode) {
        this.soundID = soundID;
        this.mode = mode;
    }

    public SoundCollision(String soundID) {
        this.soundID = soundID;
    }

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        me.sounds.triggerSound(soundID, mode);
    }

    @Override
    public CollisionFunction clone() {
        return new SoundCollision(soundID, mode);
    }
}
