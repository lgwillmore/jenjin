package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.binarymonks.jj.behaviour.Behaviour;

import static com.binarymonks.jj.demo.d01.D01_pong.BAT_HEIGHT;
import static com.binarymonks.jj.demo.d01.D01_pong.COURT_LENGTH;

/**
 * Created by lwillmore on 03/02/17.
 */
public class PlayerBehaviour extends Behaviour {

    float velocity = 30;

    @Override
    public void update() {
        float direction = 0;
        float y = parent.physicsroot.position().y;
        int upkey = (int) parent.getProperty("upkey");
        int downkey = (int) parent.getProperty("downkey");
        if (Gdx.input.isKeyPressed(upkey) && y < COURT_LENGTH - BAT_HEIGHT / 2) {
            direction += 1;
        }
        if (Gdx.input.isKeyPressed(downkey) && y > BAT_HEIGHT / 2) {
            direction -= 1;
        }
        parent.physicsroot.getB2DBody().setLinearVelocity(0, velocity * direction);
    }

    @Override
    public Behaviour clone() {
        return new PlayerBehaviour();
    }

}
