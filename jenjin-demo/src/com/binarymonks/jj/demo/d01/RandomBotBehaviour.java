package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.behaviour.Behaviour;

import static com.binarymonks.jj.demo.d01.D01_pong.BAT_HEIGHT;
import static com.binarymonks.jj.demo.d01.D01_pong.COURT_LENGTH;

public class RandomBotBehaviour extends Behaviour {


    float velocity = 20;
    boolean moving = false;

    @Override
    public void update() {
        if (!moving) {
            parent.physicsroot.getB2DBody().setLinearVelocity(0, -velocity);
            moving = true;
        }
        Vector2 position = parent.physicsroot.position();
        if (position.y > COURT_LENGTH - BAT_HEIGHT / 2) {
            parent.physicsroot.getB2DBody().setLinearVelocity(0, -velocity);
        }
        if (position.y < BAT_HEIGHT / 2) {
            parent.physicsroot.getB2DBody().setLinearVelocity(0, velocity);
        }
    }

    @Override
    public RandomBotBehaviour clone() {
        return new RandomBotBehaviour();
    }

}
