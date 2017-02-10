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

    public boolean goUp() {
        up = true;
        return true;
    }

    public boolean stopUp() {
        up = false;
        return true;
    }


    public boolean goDown() {
        down = true;
        return true;
    }

    public boolean stopDown() {
        down = false;
        return true;
    }

    @Override
    public void tearDown() {

    }

    @Override
    public Behaviour clone() {
        return new PlayerBehaviour();
    }

}
