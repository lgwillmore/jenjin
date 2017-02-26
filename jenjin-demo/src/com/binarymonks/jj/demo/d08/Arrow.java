package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;

public class Arrow extends Component {
    private Bow notchedBow;
    public Vector2 flightLocation = N.ew(Vector2.class).set(-1, 0);
    public float flightDragFactor = 0.05f;
    private int flightDragScheduleID;


    @Override
    public Component clone() {
        Arrow clone = new Arrow();
        clone.flightLocation.set(flightLocation);
        clone.flightDragFactor=flightDragFactor;
        return clone;
    }

    @Override
    public void doWork() {
//
    }

    @Override
    public void tearDown() {
        notchedBow = null;
        JJ.time.cancelScheduled(flightDragScheduleID);
    }

    @Override
    public void getReady() {
        flightDragScheduleID = JJ.time.scheduleInSeconds(this::applyFlightDrag, 1 / 60f, true);
    }

    private void applyFlightDrag() {
        Body body = parent.physicsroot.getB2DBody();
        Vector2 pointingDirection = body.getWorldVector(N.ew(Vector2.class).set(1,0));
        Vector2 flightDirection = body.getLinearVelocity();
        float flightSpeed = flightDirection.len();
        float dot = flightDirection.nor().dot(pointingDirection);
        float dragForce = (1 - Math.abs(dot)) * flightSpeed *flightSpeed*flightDragFactor * body.getMass();
        Vector2 flightPositionWorld = body.getWorldPoint(N.ew(Vector2.class).set(flightLocation));
        body.applyForce(flightDirection.scl(-1*dragForce),flightPositionWorld,true);
        Re.cycle(pointingDirection,flightPositionWorld);
    }

    public void updatePosition(Vector2 position) {
        parent.physicsroot.getB2DBody().setAwake(true);
        if (isNotched()) {
            notchedBow.updateDraw(position);
        } else {
            parent.physicsroot.setPosition(position);
        }
    }

    public boolean isNotched() {
        return notchedBow != null;
    }

    public void release() {
        if (isNotched()) {
            notchedBow.release();
        } else {
            parent.markForDestruction();
        }

    }

    public void notchIn(Bow bow) {
        notchedBow = bow;
    }
}
