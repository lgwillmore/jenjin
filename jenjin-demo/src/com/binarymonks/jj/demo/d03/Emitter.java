package com.binarymonks.jj.demo.d03;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.things.InstanceParams;


public class Emitter extends Behaviour {

    String specPath;
    float intervalSeconds;
    int scheduleID;

    public Emitter setSpecPath(String path) {
        this.specPath = path;
        return this;
    }

    public Emitter setIntervalSeconds(float intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
        return this;
    }

    @Override
    public void getReady() {
        scheduleID = JJ.time.scheduleInSeconds(this::emit, intervalSeconds);
    }

    @Override
    public void update() {

    }

    @Override
    public void tearDown() {
        JJ.time.cancelScheduled(scheduleID);
    }

    private void emit() {
        Vector2 myPos = parent.physicsroot.position();
        JJ.things.create(specPath, InstanceParams.New().setPosition(myPos.x, myPos.y));
        scheduleID = JJ.time.scheduleInSeconds(this::emit, intervalSeconds);
    }

    @Override
    public Behaviour clone() {
        return new Emitter().setIntervalSeconds(intervalSeconds).setSpecPath(specPath);
    }
}
