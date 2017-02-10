package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.binarymonks.jj.behaviour.Behaviour;

import static com.binarymonks.jj.demo.d01.D01_pong.BAT_HEIGHT;
import static com.binarymonks.jj.demo.d01.D01_pong.COURT_LENGTH;

public class PlayerBehaviour extends Behaviour {

    float velocity = 30;

    boolean up = false;
    boolean down = false;

    @Override
    public void getReady() {

    }

    @Override
    public void doWork() {
        float direction = 0;
        float y = parent.physicsroot.position().y;
        if (up && y < COURT_LENGTH - BAT_HEIGHT / 2) {
            direction += 1;
        }
        if (down && y > BAT_HEIGHT / 2) {
            direction -= 1;
        }
        parent.physicsroot.getB2DBody().setLinearVelocity(0, velocity * direction);
    }

    public void goUp() {
        up = true;
    }

    public void stopUp() {
        up = false;
    }


    public void goDown() {
        down = true;
    }

    public void stopDown() {
        down = false;
    }

    @Override
    public void tearDown() {

    }

    @Override
    public Behaviour clone() {
        return new PlayerBehaviour();
    }

}
