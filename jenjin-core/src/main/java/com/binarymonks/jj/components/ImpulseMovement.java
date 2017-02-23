package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.pools.N;

/**
 * Think of this component as an engine and steering wheel.
 * It will apply linear impulses at a constant rate.
 */
public class ImpulseMovement extends Component {

    public static float FREQUENCY_HERTZ = 30;

    private Vector2 impulse = N.ew(Vector2.class);
    private float breakForce;
    private boolean stop = false;

    private int impulseScheduleID;

    public void setImpulseVector(float x, float y) {
        stop = false;
        impulse.set(x, y);
    }

    public void stop(float breakForce) {
        this.breakForce = breakForce;
        impulse.set(0, 0);
        stop = true;
    }

    @Override
    public void doWork() {
        if (stop) {
            Vector2 opposite = parent.physicsroot.getB2DBody().getLinearVelocity().scl(-1 * breakForce);
            parent.physicsroot.getB2DBody().applyLinearImpulse(opposite, Vector2.Zero, true);
        }
    }

    @Override
    public void tearDown() {
        impulse.set(0, 0);
        JJ.time.cancelScheduled(impulseScheduleID);
    }

    @Override
    public void getReady() {
        impulseScheduleID = JJ.time.scheduleInSeconds(this::applyImpulse, 1f / FREQUENCY_HERTZ, true);
    }

    private void applyImpulse() {
        parent.physicsroot.getB2DBody().applyLinearImpulse(impulse, Vector2.Zero, true);
    }

    @Override
    public Component clone() {
        ImpulseMovement clone = new ImpulseMovement();
        return clone;
    }
}
