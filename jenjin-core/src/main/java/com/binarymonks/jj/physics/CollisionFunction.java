package com.binarymonks.jj.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingNode;

public abstract class CollisionFunction {

    CollisionResolver resolver;
    protected Array<String> ignoreProperties = new Array<>();
    protected Array<String> matchProperties = new Array<>();
    private boolean enabled = true;

    public void performCollision(Thing me, Fixture myFixture,
                                 Thing other, Fixture otherFixture, Contact contact) {
        if (enabled) {
            ThingNode gameData = (ThingNode) otherFixture.getUserData();
            for (String ignore : ignoreProperties) {
                if (gameData.hasProperty(ignore)) {
                    return;
                }
            }
            if (matchProperties.size > 0) {
                for (String matchProp : matchProperties) {
                    if (gameData.hasProperty(matchProp)) {
                        collision(me, myFixture, other, otherFixture, contact);
                        break;
                    }
                }
            } else {
                collision(me, myFixture, other, otherFixture, contact);
            }
        }
    }

    public abstract void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact);

    public abstract CollisionFunction clone();

    public void setResolver(CollisionResolver resolver) {
        this.resolver = resolver;
    }

    public void disable() {
        enabled = false;
    }

    public void enable() {
        enabled = true;
    }
}
