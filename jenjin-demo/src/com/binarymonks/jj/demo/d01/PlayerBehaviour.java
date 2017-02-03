package com.binarymonks.jj.demo.d01;

import com.binarymonks.jj.behaviour.Behaviour;

/**
 * Created by lwillmore on 03/02/17.
 */
public class PlayerBehaviour extends Behaviour{
    @Override
    public void update() {

    }

    @Override
    public Behaviour clone() {
        return new PlayerBehaviour();
    }
}
